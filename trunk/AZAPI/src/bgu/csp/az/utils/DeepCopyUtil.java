/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.csp.az.utils;

import com.ajexperience.utils.DeepCopyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bennyl
 */
public class DeepCopyUtil {
    
    //private static Cloner cloner = new Cloner();
    private static com.ajexperience.utils.DeepCopyUtil dcu = null;
    
    static {
        try {
            dcu = new com.ajexperience.utils.DeepCopyUtil();
        } catch (DeepCopyException ex) {
            Logger.getLogger(DeepCopyUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param <T>
     * @param orig
     * @return deep copy of orig using a generic deep copy framework
     */
    public static <T> T deepCopy(T orig){
        if (orig instanceof Enum || orig instanceof Throwable){
            return orig;
        }
        
        try {
            return dcu.deepCopy(orig);//cloner.deepClone(orig);
        } catch (DeepCopyException ex) {
            Logger.getLogger(DeepCopyUtil.class.getName()).log(Level.SEVERE, null, ex);
            return orig;
        }
    }
}