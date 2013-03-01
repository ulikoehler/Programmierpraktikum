package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

/**
 * 
 * @author spoeri
 */
public class Data {
    
    
    public static int trainingWindowSize = 17;
    
    /**
     * prevInWindow + middle(1) + rest = trainingWindowSize
     */
    public static int prevInWindow = 8;
    
    /**
     * one letter code of all AA
     */
    final public static char[] aaTable = {
        'A', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'K', 'L',
        'M', 'N', 'P', 'Q', 'R',
        'S', 'T', 'V', 'W', 'Y',
    };
    
    public static char[] secStruct = {
        'C', 'E', 'H'
    };
    
    public static final String[] colors = {
        "#f00", "#0f0", "#00f", "#ff0", "#f0f", "#0ff", "#fc0", "#f0c", "#0fc", "#cf0", "#c0f", "#800000", "#ffA500", "#808000",
        "#800080", "#008000", "#008080", "#c0c0c0", "#808080", "#990066", "#000000", "#996600", "#99CCCC", "#FF0066", "#FF9999", "#CC33FF"
    };
}
