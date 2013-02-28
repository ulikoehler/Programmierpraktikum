package de.bioinformatikmuenchen.pg4.ssp.ssptrain;

/**
 * 
 * @author spoeri
 */
public class Data {
    
    
    public static final int triaingWindowSize = 17;
    public static final int prevInWindow = 8;
    
    /**
     * one letter code of all AA
     */
    final public static char[] AcTable = {
        'A', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'K', 'L',
        'M', 'N', 'P', 'Q', 'R',
        'S', 'T', 'V', 'W', 'Y',
    };
    
    final public static char[] secStruct = {
        'C', 'E', 'H'
    };
    
}
