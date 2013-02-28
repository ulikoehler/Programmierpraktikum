/*
 * GOR3 Trainer
 */
package de.bioinformatikmuenchen.pg4.ssp.ssptrain;

import static de.bioinformatikmuenchen.pg4.ssp.ssptrain.Trainer.convertASCharToMatrixId;
import static de.bioinformatikmuenchen.pg4.ssp.ssptrain.Trainer.convertStructureCharToMatrixId;

/**
 *
 * @author spoeri
 */
public class TrainerGor4 extends Trainer {

    private int[][][][] cMatrix;

    @Override
    public void init() {
        cMatrix = new int[Data.AcTable.length][Data.secStruct.length][Data.triaingWindowSize][Data.AcTable.length];
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        // middle of window
        char mA = aminoSeq.charAt(Data.prevInWindow + 1);
        char mSG = secStruct.charAt(Data.prevInWindow + 1);
        for (int i = 0; i < Data.triaingWindowSize; i++) {
            char sL = aminoSeq.charAt(i);
            cMatrix[convertASCharToMatrixId(mA)][convertStructureCharToMatrixId(mSG)][i][convertASCharToMatrixId(sL)]++;
        }
    }

    @Override
    public String getMatrixRepresentation() {

        String result = "// Matrix 4D (" + Data.secStruct.length + "x" + Data.triaingWindowSize + "x" + Data.AcTable.length + ")\n\n";
        for (int aa = 0; aa < Data.AcTable.length; aa++) {
            for (int m = 0; m < Data.secStruct.length; m++) {
                result += "=" + Data.AcTable[aa] + "," + Data.secStruct[m] + "=\n\n";
                for (int as = 0; as < Data.AcTable.length; as++) {
                    result += Data.AcTable[as] + "\t";
                    for (int ws = 0; ws < Data.triaingWindowSize; ws++) {
                        result += cMatrix[aa][m][ws][as] + "\t";
                    }
                    result += "\n";
                }
                result += "\n\n";
            }
        }
        return result;

    }
}
