/*
 * GOR1 Trainer
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;
import java.io.File;

/**
 *
 * @author spoeri
 */
public class TrainerGor1 extends Trainer {

    private int[][][] matrix = new int[3][17][20];

    private static int convertStructureCharToMatrixId(char x) {
        if (x == 'C') {
            return 0;
        } else if (x == 'E') {
            return 1;
        } else if (x == 'H') {
            return 2;
        }
        throw new RuntimeException("invalid character in structure sequence!");
    }
    
    private static int convertASCharToMatrixId(char x) {
        for(int i = 0; i < AminoAcid.AcTable.length; i++) {
            if(AminoAcid.AcTable[i] == x)
                return i;
        }
        throw new RuntimeException("invalid character in amino acid sequence!");
    }

    @Override
    public void init() {
        
    }

    @Override
    public void train1Example(String aminoSeq, String secStruct) {
        System.out.println(aminoSeq + " <-> " + secStruct);
    }

    @Override
    public void writeMatrixToFile(File f) {
        
    }

}
