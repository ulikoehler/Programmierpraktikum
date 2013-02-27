/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.distance;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.HashMap;
import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author koehleru
 */
public class QUASARDistanceMatrixFactory {

    private static final Function<String, Double> DotTrailedStringToDoubleFunc = new Function<String, Double>() {
        public Double apply(String f) {
            //Some quasar files (current official ones) contain dots at the end of each dataset
            if (f.endsWith(".")) {
                f = f.substring(0, f.length() - 1);
                assert !f.endsWith(".");
            }
            return Double.parseDouble(f);
        }
    };

    public static IDistanceMatrix factorize(String filename) throws IOException {
        return factorize(new FileReader(filename));
    }

    /**
     * Factorize a new distance matrix from a QUASAR matrix file...
     *
     * @param fileReader The reader to read data from -- closed when finished!
     */
    public static IDistanceMatrix factorize(Reader fileReader) throws IOException {
        //Where the AA/nucl -> dist mappings will be placed.
        //Refer to MapDistanceMatrix for further description
        HashMap<String, Double> mapping = Maps.newHashMap();
        //Some other fwd decls
        String name = null;
        int xSize = 0; //numcols
        int ySize = 0; //numrows
        String rowIndex = null;
        String colIndex = null;
        //Start
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        int currentMatrixLineIndex = 0; //We're currently on the nth matrix line
        int lineCounter = 0;
        while ((line = reader.readLine()) != null) {
            lineCounter++;
            System.out.println("L: " + line);
            if (line.startsWith("NAME ")) {
                name = line.substring("NAME ".length());
            } else if (line.startsWith("NUMROW")) {
                xSize = Integer.parseInt(line.substring("NUMROW".length()).trim());
            } else if (line.startsWith("NUMCOL")) {
                ySize = Integer.parseInt(line.substring("NUMCOL".length()).trim());
            } else if (line.startsWith("ROWINDEX")) {
                rowIndex = line.substring("ROWINDEX".length()).trim();
            } else if (line.startsWith("COLINDEX ")) {
                colIndex = line.substring("COLINDEX".length()).trim();
            } else if (line.startsWith("MATRIX")) {
                assert rowIndex != null;
                assert colIndex != null;
                line = line.substring("MATRIX".length()).trim(); //Remove MATRIX prefix
                //Split and transform to doubles (guava)
                List<String> stringValues = Lists.newArrayList(Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults().split(line));
                ArrayList<Double> values = Lists.newArrayList(Collections2.transform(stringValues, DotTrailedStringToDoubleFunc));
                //Insert the data into the mapping
                for (int i = 0; i < values.size(); i++) {
                    mapping.put(rowIndex.charAt(currentMatrixLineIndex) + "-" + colIndex.charAt(i), values.get(i));
                    mapping.put(colIndex.charAt(i) + "-" + rowIndex.charAt(currentMatrixLineIndex), values.get(i));
                    System.out.println(rowIndex.charAt(currentMatrixLineIndex) + "-" + colIndex.charAt(i) + "= " + values.get(i));
                }
                currentMatrixLineIndex++;
            }
        }
        //If we don't have a row or col index, fail (e.g. empty input file or other format)
        if (rowIndex == null || colIndex == null) {
            throw new IllegalStateException("Could not read QUASAR data from substitution matrix file -- read overall " + lineCounter + " lines");
        }
//        System.out.println("Read " + lineCounter + " lines");
        reader.close();
        return new MapDistanceMatrix(mapping);
    }
}
