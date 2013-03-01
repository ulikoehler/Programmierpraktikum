/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

/**
 *
 * @author koehleru
 */
public class AlignmentException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>AlignmentException</code> without detail message.
     */
    public AlignmentException() {
    }

    /**
     * Constructs an instance of
     * <code>AlignmentException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AlignmentException(String msg) {
        super(msg);
    }
    
    
    public AlignmentException(String msg, Exception cause) {
        super(msg, cause);
    }
}
