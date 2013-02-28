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
public class TrainerGor3 extends Trainer {

    private long[][][][] cMatrix;

    @Override
    public void init() {
        cMatrix = new long[Data.aaTable.length][Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        // middle of window
        char mA = aminoSeq.charAt(Data.prevInWindow);   // Note, that Java starts counting by 0!
        char mSG = secStruct.charAt(Data.prevInWindow);
        for (int i = 0; i < Data.trainingWindowSize; i++) {
            char sL = aminoSeq.charAt(i);
            if (convertASCharToMatrixId(mA) == -1 || convertStructureCharToMatrixId(mSG) == -1 || convertASCharToMatrixId(sL) == -1) {
                continue;
            }
            cMatrix[convertASCharToMatrixId(mA)][convertStructureCharToMatrixId(mSG)][i][convertASCharToMatrixId(sL)]++;
        }
    }

    @Override
    public String getMatrixRepresentation() {

        StringBuilder result = new StringBuilder("// Matrix4D\n\n");
        for (int aa = 0; aa < Data.aaTable.length; aa++) {
            for (int m = 0; m < Data.secStruct.length; m++) {
                result.append("=").append(Data.aaTable[aa]).append(",").append(Data.secStruct[m]).append("=\n\n");
                for (int as = 0; as < Data.aaTable.length; as++) {
                    result.append(Data.aaTable[as]).append("\t");
                    for (int ws = 0; ws < Data.trainingWindowSize; ws++) {
                        result.append(cMatrix[aa][m][ws][as]).append("\t");
                    }
                    result.append("\n");
                }
                result.append("\n\n");
            }
        }
        return result.toString();

    }
}
