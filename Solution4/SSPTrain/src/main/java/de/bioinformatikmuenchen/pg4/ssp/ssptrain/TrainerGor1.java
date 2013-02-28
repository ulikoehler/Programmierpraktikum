/*
 * GOR1 Trainer
 */
package de.bioinformatikmuenchen.pg4.ssp.ssptrain;

import static de.bioinformatikmuenchen.pg4.ssp.ssptrain.Trainer.convertASCharToMatrixId;
import static de.bioinformatikmuenchen.pg4.ssp.ssptrain.Trainer.convertStructureCharToMatrixId;
import java.io.File;

/**
 *
 * @author spoeri
 */
public class TrainerGor1 extends Trainer {

    private long[][][] cMatrix;

    @Override
    public void init() {
        cMatrix = new long[Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        // middle of window
        char m = secStruct.charAt(Data.prevInWindow);   // Note, that Java starts counting by 0!
        for (int i = 0; i < Data.trainingWindowSize; i++) {
            char s = aminoSeq.charAt(i);
            if (convertStructureCharToMatrixId(m) == -1 || convertASCharToMatrixId(s) == -1) {
                continue;
            }
            cMatrix[convertStructureCharToMatrixId(m)][i][convertASCharToMatrixId(s)]++;
        }
    }

    @Override
    public String getMatrixRepresentation() {

        StringBuilder result = new StringBuilder("// Matrix3D\n\n");
        for (int m = 0; m < Data.secStruct.length; m++) {
            result.append("=").append(Data.secStruct[m]).append("=\n\n");
            for (int as = 0; as < Data.aaTable.length; as++) {
                result.append(Data.aaTable[as]).append("\t");
                for (int ws = 0; ws < Data.trainingWindowSize; ws++) {
                    result.append(cMatrix[m][ws][as]).append("\t");
                }
                result.append("\n");
            }
            result.append("\n\n");
        }
        return result.toString();

    }
}
