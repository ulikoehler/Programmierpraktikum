/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive;

import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;

/**
 * Matrix from "A modern presentation" section of
 * http://en.wikipedia.org/wiki/Needleman%E2%80%93Wunsch_algorithm
 *
 * @author koehleru
 */
public class WikipediaAlignmentMatrix1 implements IDistanceMatrix {

    public double distance(char A, char B) {
        if (A == 'A') {
            if (B == 'A') {
                return 10;
            } else if (B == 'G') {
                return -1;
            } else if (B == 'C') {
                return -3;
            } else if (B == 'T') {
                return -4;
            } else {
                throw new IllegalArgumentException("Can't find distance of " + A + " and " + B + " (b unknown)!");
            }
        } else if (A == 'G') {
            if (B == 'A') {
                return -1;
            } else if (B == 'G') {
                return 7;
            } else if (B == 'C') {
                return -5;
            } else if (B == 'T') {
                return 3;
            } else {
                throw new IllegalArgumentException("Can't find distance of " + A + " and " + B + " (b unknown)!");
            }
        } else if (A == 'C') {
            if (B == 'A') {
                return -3;
            } else if (B == 'G') {
                return -5;
            } else if (B == 'C') {
                return 9;
            } else if (B == 'T') {
                return 0;
            } else {
                throw new IllegalArgumentException("Can't find distance of " + A + " and " + B + " (b unknown)!");
            }
        } else if (A == 'T') {
            if (B == 'A') {
                return -4;
            } else if (B == 'G') {
                return -3;
            } else if (B == 'C') {
                return 0;
            } else if (B == 'T') {
                return 8;
            } else {
                throw new IllegalArgumentException("Can't find distance of " + A + " and " + B + " (b unknown)!");
            }
        } else {
            throw new IllegalArgumentException("Can't find distance of " + A + " and " + B + " (a unknown)!");
        }
    }

    public boolean isAminoAcidMatrix() {
        return false;
    }

    public String getName() {
        return "Random matrix from Wikipedia's NW description, do not use for production";
    }
}
