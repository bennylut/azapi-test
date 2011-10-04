/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bc.swing.pfrm.ano;

import bc.swing.pfrm.viewtypes.ParamType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * used with: m.invoke(getPage().getModel(), dataName, from, newValue);
 * @author BLutati
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataInserter {
    String[] columns() default {};
    String param();
}
