package net.timardo.contentcreator.loader;

/**
 * I guess I'll delete this
 * 
 * @author Timardo
 *
 */
public class AddonLoadingException extends Exception {
    public String message;
    
    public AddonLoadingException(String msg) {
        this.message = msg;
    }
}
