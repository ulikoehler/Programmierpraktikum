package de.bioinformatikmuenchen.pg4.alignment.gap;

/**
 *
 * @author koehleru
 */
public interface IGapCost {
    /**
     * @return the gap cost for opening a gap of the specified length, including the gap open penalty
     */
    public double getGapCost(int length);
    public double getGapExtensionPenalty(int previousLength, int extendBy);
}
