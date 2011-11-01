/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bc.dsl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bennyl
 */
public class ReflectionDSL {

    public static Method methodWithAnnotation(Class c, Class<? extends Annotation> a) {
        for (Method m : c.getDeclaredMethods()) {

            if (m.isAnnotationPresent(a)) {
                m.setAccessible(true);
                return m;
            }
        }

        return null;
    }

    public static Set<Class> getClassGraph(Class c){
        Set<Class> ret = new HashSet<Class>();
        LinkedList<Class> investigateQ = new LinkedList<Class>();
        investigateQ.add(c);
        while (!investigateQ.isEmpty()){
            Class i = investigateQ.removeFirst();
            
            if (ret.contains(i)) continue;
            ret.add(i);
            
            Class[] interfaces = i.getInterfaces();
            Class superclass = i.getSuperclass();
            
            if (superclass != null && !ret.contains(superclass)){
                investigateQ.add(superclass);
            }
            
            for (Class inter : interfaces){
                if (!ret.contains(inter)){
                    investigateQ.add(inter);
                }
            }
        }
        
        return ret;
    }
    
    public static List<Method> getAllMethodsWithAnnotation(Class aClass, Class<? extends Annotation> ano) {
        LinkedList<Method> ret = new LinkedList<Method>();
        for (Method m : aClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ano)) {
                m.setAccessible(true);
                ret.add(m);
            }
        }

        return ret;
    }
    
    public static List<Field> getAllFieldsWithAnnotation(Class aClass, Class<? extends Annotation> ano) {
        LinkedList<Field> ret = new LinkedList<Field>();
        for (Field m : aClass.getDeclaredFields()) {
            if (m.isAnnotationPresent(ano)) {
                m.setAccessible(true);
                ret.add(m);
            }
        }

        return ret;
    }
    
    public static Method methodByName(Class aClass, String name){
        LinkedList<Method> ret = new LinkedList<Method>();
        for (Method m : aClass.getDeclaredMethods()) {
            if (m.getName().equals(name)) {
                m.setAccessible(true);
                return m;
            }
        }
        
        return null;
    }
    
    public static Method methodByNameAndNArgs(Class aClass, String name, int nargs){
        for (Method m : methodsByName(aClass, name)){
            if (m.getParameterTypes().length == nargs) return m;
        }
        
        return null;
    }

    public static List<Method> methodsByName(Class aClass, String name){
        LinkedList<Method> ret = new LinkedList<Method>();
        for (Method m : aClass.getDeclaredMethods()) {
            if (m.getName().equals(name)) {
                m.setAccessible(true);
                ret.add(m);
            }
        }

        return ret;
    }

}