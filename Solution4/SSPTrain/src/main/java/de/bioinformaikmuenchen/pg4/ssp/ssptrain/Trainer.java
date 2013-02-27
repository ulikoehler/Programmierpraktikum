/*
 * abstract class Trainer
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import de.bioinformatikmuenchen.pg4.common.AminoAcid;

/**
 *
 * @author spoeri
 */
public abstract class Trainer {

    public long matrix[][][];
    public File inputFile;
    public File outputFile;

    public void parseFile() {
        // get data from file
        try {
            BufferedReader file = new BufferedReader(new FileReader("inputFile"));
            String strLine = "", id = "", aminoSeq = "", secStruct = "";
            while ((strLine = file.readLine()) != null) {
                if(strLine.startsWith(">")) {
                    // save id?
                    if(!id.isEmpty() && !aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                        parseAminoSeq(id, aminoSeq, secStruct);
                    }
                    id = strLine.substring(2);
                    aminoSeq = "";
                    secStruct = "";
                } else if(strLine.startsWith("AS ")) {
                    aminoSeq = strLine.substring(3);
                } else if(strLine.startsWith("SS ")) {
                    secStruct = strLine.substring(3);
                }
            }
            // save (last) id?
            if(!id.isEmpty() && !aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                parseAminoSeq(id, aminoSeq, secStruct);
            }
            file.close();
        } catch (IOException err) {
            System.err.println("ERROR READING FILE!\n");
            System.exit(1);
        }
    }

    public abstract void parseAminoSeq(String id, String aminoSeq, String SecStruct);

    public abstract void trainMatrix();

    public long[][][] getMatrix() {
        return matrix;
    }

    public void printMatrixToBash() {
        // for each matrix
        for (int m = 0; m < matrix.length; m++) {
            System.out.println("Matrix: (" + m + "): ");

            // output spec matrix
            for (int x = 0; x < matrix[m].length; m++) {
                System.out.println((AminoAcid.AC1[x]) + "\t");
                for (int y = 0; y < matrix[m][x].length; m++) {
                    System.out.print(matrix[m][x][y] + "\t");
                }
                System.out.println();
            }
        }
    }
}
