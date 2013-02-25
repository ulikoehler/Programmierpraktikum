package de.bioinformaikmuenchen.pg4.common;

/**
 * 
 * @author spoeri
 */
public class AminoAcid {
    
    /**
     * one letter code of all AA
     */
    public static char[] AC1 = {
        'A', 'R', 'N', 'D', 'B', 'C',
        'E', 'Q', 'Z', 'G', 'H', 'I',
        'L', 'K', 'M', 'F', 'P', 'S',
        'T', 'W', 'Y', 'V'
    };
    
    /**
     * three letter code of each AA
     */
    public static String[] AC3 = {
        "ala", "arg", "asn", "asp", "asx", "cys",
        "glu", "gln", "glx", "gly", "his", "ile",
        "leu", "lys", "met", "phe", "pro", "ser",
        "thr", "trp", "tyr", "val"
    };
    
    /**
     * full names of each AA
     */
    public static String[] ACf = {
        "alanine", "arginine", "asparagine", "aspartic acid", "asparagine or aspartic acid", "cysteine",
        "glutamic acid", "glutamine", "glutamine or glutamic acid", "glycine", "histidine", "isoleucine",
        "leucine", "lysine", "methionine", "phenylalanine", "proline", "serine",
        "threonine", "tryptophan", "tyrosine", "valine"
    };
}