package bgu.dcr.az.anop;

import bgu.dcr.az.anop.conf.JavaDocInfo;
import bgu.dcr.az.anop.utils.CodeUtils;
import bgu.dcr.az.anop.utils.JavaDocParser;
import bgu.dcr.az.anop.utils.ProcessorUtils;
import bgu.dcr.az.anop.utils.StringBuilderWriter;
import bgu.dcr.az.anop.utils.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.mvel2.ParserContext;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import resources.templates.ResourcesTemplatesAncor;

/**
 *
 * @author User
 */
@SupportedAnnotationTypes("bgu.dcr.az.anop.Register")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RegisteryAnnotationProcessor extends AbstractProcessor {

    private static boolean run = false;

    public static final String AUTOGEN_PACKAGE = "bgu.dcr.autogen";
    private Messager msg;

    private final CompiledTemplate registeryTemplate;
    private final CompiledTemplate configurationTemplate;
    private final Map<String, TypeElement> configurableClasses = new HashMap<>();

    public RegisteryAnnotationProcessor() {
        ParserContext ctx = ParserContext.create();
        ctx.addImport(CodeUtils.class);
        registeryTemplate = TemplateCompiler.compileTemplate(ResourcesTemplatesAncor.class.getResourceAsStream("CompiledRegistery.javat"));
        configurationTemplate = TemplateCompiler.compileTemplate(ResourcesTemplatesAncor.class.getResourceAsStream("AbstractConfigurationTemplete.javat"), ctx);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (run) {
            return false;
        }
        ProcessorUtils.initialize(processingEnv);
        run = true;
        msg = processingEnv.getMessager();

        List<RegisteredClass> registeredClasses = new LinkedList<>();
        findConfigurableClasses(roundEnv);

        for (TypeElement te : configurableClasses.values()) {
            createConfiguration(te);
            registeredClasses.add(new RegisteredClass(te.getQualifiedName().toString(), te.getAnnotation(Register.class).value()));
        }

        //create context
        Map context = new HashMap();
        context.put("packageName", AUTOGEN_PACKAGE);
        context.put("registrations", registeredClasses);

        writeClass(AUTOGEN_PACKAGE + ".CompiledRegistery", registeryTemplate, context);

        return true;
    }

    private void findConfigurableClasses(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Register.class)) {
            note("Scanning: " + element);

            if (element instanceof TypeElement) {
                configurableClasses.put("" + element, (TypeElement) element);
            }
        }
    }

    private void writeClass(String classFQN, CompiledTemplate codeTemplate, Map context) {
        String out = (String) TemplateRuntime.execute(codeTemplate, context);
        writeClass(classFQN, out);
    }

    private void writeClass(String classFQN, String code) {
        Filer filler = processingEnv.getFiler();
        try {
            JavaFileObject source = filler.createSourceFile(classFQN);
//            msg.printMessage(Diagnostic.Kind.NOTE, "creating "+ source.toUri());
            try (Writer w = source.openWriter()) {
                w.append(code);
                w.flush();
            }

        } catch (IOException ex) {
            StringBuilderWriter writer = new StringBuilderWriter();
            ex.printStackTrace(new PrintWriter(writer));
            msg.printMessage(Diagnostic.Kind.ERROR, "cannot generate source file:\n" + writer.toString());
        }
    }

    private void createConfiguration(TypeElement te) {

        final Map ctx = new HashMap();
        final List<PropertyInfo> properties = new LinkedList<>();

        ctx.put("typeInfo", ProcessorUtils.extractClassTypeName(te, true));
        ctx.put("className", fqnToConfigurationClassName(te.getQualifiedName().toString()));
        ctx.put("properties", properties);
        ctx.put("configuredClassName", te.getQualifiedName().toString());
        ctx.put("escapedJavadoc", StringUtils.escapedString(ProcessorUtils.extractJavadoc(te)));
        ctx.put("extension", null);
        ctx.put("extensionConfiurationClass", null);

        TypeMirror parent = te.getSuperclass();
        String parentFQN = "" + parent;
        if (configurableClasses.containsKey(parentFQN)) {
            ctx.put("extension", parentFQN);
            ctx.put("extensionConfiurationClass", AUTOGEN_PACKAGE + "." + fqnToConfigurationClassName(parentFQN));
        }

        Map<String, ExecutableElement> methods = ProcessorUtils.extractMethods(te);
        for (Map.Entry<String, ExecutableElement> p : methods.entrySet()) {
            if (p.getKey().startsWith("get")) {
                PropertyInfo info = new PropertyInfo();
                info.description = "";
                info.displayName = p.getValue().getSimpleName().toString().substring("get".length());
                info.iconPath = "";
                info.name = info.displayName;
                info.getter = p.getKey();
                info.setter = "";
                info.type = ProcessorUtils.extractTypeUnparametrizedFQN(p.getValue().getReturnType());
                info.typeFQN = ProcessorUtils.extractClassTypeName(p.getValue().getReturnType(), true);

                final String setterName = "set" + p.getKey().substring("get".length());
                if (methods.containsKey(setterName)) {
                    info.setter = setterName;
                }

//                note("adding " + p.getKey() + " with doc: " + processingEnv.getElementUtils().getDocComment(p.getValue()));
//                JavaDocInfo jd = JavaDocParser.parse(processingEnv.getElementUtils().getDocComment(p.getValue()));
//                note(jd.description());
//                for (String t : jd.tags()) {
//                    note(t + ":\n" + jd.tag(t));
//                }
                
                properties.add(info);
            }
        }

        writeClass(AUTOGEN_PACKAGE + "." + ctx.get("className"), configurationTemplate, ctx);

    }

    public static String fqnToConfigurationClassName(String fqn) {
        return fqn.replaceAll("\\.", "_");
    }

    public void note(String note) {
        msg.printMessage(Diagnostic.Kind.NOTE, note);
    }

    public static class RegisteredClass {

        public String clazz;
        public String regName;

        public RegisteredClass(String clazz, String regName) {
            this.clazz = clazz;
            this.regName = regName;
        }

    }

    public static class PropertyInfo {

        public String name;
        public String type;
        public String typeFQN;
        public String displayName, iconPath, description;
        public String setter;
        public String getter;

    }

}
