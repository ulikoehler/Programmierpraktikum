/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.distance;

import java.util.HashMap;

/**
 * A package-private distance matrix that uses a HashMap backend. The key of the
 * hasmap is (query1 + "-" + query2)
 *
 * @author koehleru
 */
class MapDistanceMatrix implements IDistanceMatrix {

    private HashMap<String, Double> map;
    private boolean isAAMatrix = false;
    private String name = null;

    public MapDistanceMatrix(HashMap<String, Double> map) {
        this(map, null);
    }

    public MapDistanceMatrix(HashMap<String, Double> map, String name) {
        this.map = map;
        this.name = name;
        autoDetermineMatrixType();
    }

    private void autoDetermineMatrixType() {
        //Check if there's a mapping for Serine -> Serine
        isAAMatrix = map.containsKey("SS");
    }

    public double distance(char A, char B) {
        String key = Character.toString(A) + "-" + B;
        try {
            return map.get(key);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Can't find distance between " + A + " and " + B + " - using wrong matrix type?", ex);
        }
    }

    public boolean isAminoAcidMatrix() {
        return isAAMatrix;
    }

    public String getName() {
        return name;
    }
}
