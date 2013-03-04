/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

/**
 *
 * @author koehleru
 */
public class SSAADataInvalidException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>AlignmentException</code> without detail message.
     */
    public SSAADataInvalidException() {
    }

    /**
     * Constructs an instance of
     * <code>AlignmentException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SSAADataInvalidException(String msg) {
        super(msg);
    }
    
    
    public SSAADataInvalidException(String msg, Exception cause) {
        super(msg, cause);
    }
}
