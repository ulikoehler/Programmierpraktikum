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

    private long[][][][][][] cMatrix;

    @Override
    public void init() {
        // [amino acid somewhere in window][position of acid somewhere in window][amino acid in the middle][amino acid relative to the middle][position relative to the middle][secondary state in middle] = count
        cMatrix = new long[Data.aaTable.length][Data.trainingWindowSize][Data.aaTable.length][Data.aaTable.length][Data.trainingWindowSize][Data.secStruct.length];
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        // middle of window
        // Note, that Java starts counting by 0 (charAt)!
        char aaMiddle = aminoSeq.charAt(Data.prevInWindow);
        char ssMiddle = secStruct.charAt(Data.prevInWindow);
        // foreach other positions
        for (int posOtherAa = 0; posOtherAa < Data.trainingWindowSize; posOtherAa++) {
            char asOther = aminoSeq.charAt(posOtherAa);

            for (int posRel = posOtherAa + 1; posRel < Data.trainingWindowSize; posRel++) {
                char aaRel = aminoSeq.charAt(posRel);

                if (convertStructureCharToMatrixId(ssMiddle) == -1 || convertASCharToMatrixId(aaMiddle) == -1 || convertASCharToMatrixId(asOther) == -1 || convertASCharToMatrixId(aaRel) == -1) {
                    continue;
                }
                cMatrix[convertASCharToMatrixId(asOther)][posOtherAa][convertASCharToMatrixId(aaMiddle)][convertASCharToMatrixId(aaRel)][posRel][convertStructureCharToMatrixId(ssMiddle)]++;
            }
        }
    }

    @Override
    public String getMatrixRepresentation() {

        // =State, AS, AS, Pos=
        //        POS
        //  AS   count

        StringBuilder result = new StringBuilder("// Matrix6D\n\n");

        for (int ss = 0; ss < Data.secStruct.length; ss++) {
            for (int aaAbove = 0; aaAbove < Data.aaTable.length; aaAbove++) {
                for (int aaSom = 0; aaSom < Data.aaTable.length; aaSom++) {
                    for (int aaSomPos = 0; aaSomPos < Data.trainingWindowSize; aaSomPos++) {
                        result.append("=").append(Data.secStruct[ss]).append(",").append(Data.aaTable[aaAbove]).append(",").append(Data.aaTable[aaSom]).append(",").append(aaSomPos - Data.prevInWindow).append("=\n\n");
                        for (int aaRel = 0; aaRel < Data.aaTable.length; aaRel++) {
                            result.append(Data.aaTable[aaRel]).append("\t");
                            for (int relPos = 0; relPos < Data.trainingWindowSize; relPos++) {
                                result.append(cMatrix[aaSom][aaSomPos][aaAbove][aaRel][relPos][ss]).append("\t");
                            }
                            result.append('\n');
                        }
                        result.append("\n");
                    }
                }
            }
        }

        return result.toString();

    }
}
