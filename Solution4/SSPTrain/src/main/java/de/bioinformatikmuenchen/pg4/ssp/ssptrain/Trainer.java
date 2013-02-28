/*
 * abstract class Trainer
 */
package de.bioinformatikmuenchen.pg4.ssp.ssptrain;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author spoeri
 */
public abstract class Trainer {

    public abstract void init();

    public void parseFileAndTrain(File inputFile) {
        if (inputFile == null) {
            throw new RuntimeException("No input File specificated @GORTrainer!");
        }
        // get data from file
        try {
            BufferedReader file = new BufferedReader(new FileReader(inputFile));
            String strLine = "", aminoSeq = "", secStruct = "";
            while ((strLine = file.readLine()) != null) {
                if (strLine.startsWith(">")) {
                    // id isn't interesting so just parse SS/AS
                    if (!aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                        trainOneSeq(aminoSeq, secStruct);
                    }
                    aminoSeq = "";
                    secStruct = "";
                } else if (strLine.startsWith("AS ")) {
                    aminoSeq = strLine.substring(3);
                } else if (strLine.startsWith("SS ")) {
                    secStruct = strLine.substring(3);
                }
            }
            // don't forget the (last) id?
            if (!aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                trainOneSeq(aminoSeq, secStruct);
            }
            file.close();
        } catch (IOException err) {
            System.err.println("ERROR READING INPUT FILE!\n" + err.toString());
            System.exit(1);
        }
    }

    public void trainOneSeq(String aminoSeq, String secStruct) {
        if (aminoSeq.length() != secStruct.length()) {
            throw new RuntimeException("Error; AC Sequence and SS Sequence differ in length!");
        }
        for (int startPos = 0; startPos < aminoSeq.length() - Data.trainingWindowSize + 1; startPos++) {
            trainOneExample(aminoSeq.substring(startPos, startPos + Data.trainingWindowSize), secStruct.substring(startPos, startPos + Data.trainingWindowSize));
        }
    }

    public void trainOneExample(String aminoSeq, String secStruct) {
        if (aminoSeq.length() != Data.trainingWindowSize || secStruct.length() != Data.trainingWindowSize) {
            throw new RuntimeException("Error; AC Sequence and SS Sequence substr didn't have a length of " + Data.trainingWindowSize + " (" + aminoSeq + ", " + secStruct + ")!");
        }
        train1Example(aminoSeq, secStruct);
    }

    public abstract void train1Example(String aminoSeq, String secStruct);

    public void writeMatrixToFile(File f) {
        try {
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(getMatrixRepresentation());
            out.close();
        } catch (Exception e) {
            throw new RuntimeException("Error writing to model file! " + e.toString());
        }
    }

    public abstract String getMatrixRepresentation();

    public static int convertStructureCharToMatrixId(char x) {
        for (int i = 0; i < Data.secStruct.length; i++) {
            if (Data.secStruct[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public static int convertASCharToMatrixId(char x) {
        for (int i = 0; i < Data.aaTable.length; i++) {
            if (Data.aaTable[i] == x) {
                return i;
            }
        }
        return -1;
    }
}
