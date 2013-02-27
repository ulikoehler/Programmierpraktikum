package de.bioinformatikmuenchen.pg4.common.distance;

/**
 *
 * @author koehleru
 */
public interface IDistanceMatrix {
    /**
     * @return The distance between the A and B nucleic acids / aminoacids if (@see isAminoAcidMatrix() == true)
     */
    double distance(char A, char B);
    /**
     * @return true if and only if this matrix is an amino acid matrix
     */
    boolean isAminoAcidMatrix();
    /**
     * @return The name of this matrix or null if not specified
     */
    String getName();
}
