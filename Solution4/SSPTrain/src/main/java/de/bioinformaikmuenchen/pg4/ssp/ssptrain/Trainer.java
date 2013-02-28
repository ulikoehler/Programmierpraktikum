/*
 * abstract class Trainer
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

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
            BufferedReader file = new BufferedReader(new FileReader("inputFile"));
            String strLine = "", id = "", aminoSeq = "", secStruct = "";
            while ((strLine = file.readLine()) != null) {
                if (strLine.startsWith(">")) {
                    // save id?
                    if (!id.isEmpty() && !aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                        trainOneSeq(id, aminoSeq, secStruct);
                    }
                    id = strLine.substring(2);
                    aminoSeq = "";
                    secStruct = "";
                } else if (strLine.startsWith("AS ")) {
                    aminoSeq = strLine.substring(3);
                } else if (strLine.startsWith("SS ")) {
                    secStruct = strLine.substring(3);
                }
            }
            // don't forget the (last) id?
            if (!id.isEmpty() && !aminoSeq.isEmpty() && !secStruct.isEmpty()) {
                trainOneSeq(id, aminoSeq, secStruct);
            }
            file.close();
        } catch (IOException err) {
            System.err.println("ERROR READING INPUT FILE!\n");
            System.exit(1);
        }
    }

    public void trainOneSeq(String id, String aminoSeq, String secStruct) {
        if (aminoSeq.length() != secStruct.length()) {
            throw new RuntimeException("Error; AC Sequence and SS Sequence differ in length!");
        }
        for (int startPos = 0; startPos < aminoSeq.length() - 19; startPos++) {
            trainOneExample(aminoSeq.substring(startPos, 19), secStruct.substring(startPos, 19));
        }
    }

    public void trainOneExample(String aminoSeq, String secStruct) {
        if (aminoSeq.length() != 17 || secStruct.length() != 17) {
            throw new RuntimeException("Error; AC Sequence and SS Sequence substr didn't have a length of 17!");
        }
        train1Example(aminoSeq, secStruct);
    }

    public abstract void train1Example(String aminoSeq, String secStruct);

    public abstract void writeMatrixToFile(File f);
}
