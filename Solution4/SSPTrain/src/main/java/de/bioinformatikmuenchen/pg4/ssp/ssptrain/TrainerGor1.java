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

    private int[][][] cMatrix;

    @Override
    public void init() {
        cMatrix = new int[Data.secStruct.length][Data.triaingWindowSize][Data.AcTable.length];
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        // middle of window
        char m = secStruct.charAt(Data.prevInWindow);
        for (int i = 0; i < Data.triaingWindowSize; i++) {
            char s = aminoSeq.charAt(i);
            if (convertStructureCharToMatrixId(m) == -1 || convertASCharToMatrixId(s) == -1) {
                continue;
            }
            cMatrix[convertStructureCharToMatrixId(m)][i][convertASCharToMatrixId(s)]++;
        }
    }

    @Override
    public String getMatrixRepresentation() {

        String result = "// Matrix 3D (" + Data.secStruct.length + "x" + Data.triaingWindowSize + "x" + Data.AcTable.length + ")\n\n";
        for (int m = 0; m < Data.secStruct.length; m++) {
            result += "=" + Data.secStruct[m] + "=\n\n";
            for (int as = 0; as < Data.AcTable.length; as++) {
                result += Data.AcTable[as] + "\t";
                for (int ws = 0; ws < Data.triaingWindowSize; ws++) {
                    result += cMatrix[m][ws][as] + "\t";
                }
                result += "\n";
            }
            result += "\n\n";
        }
        return result;

    }
}
