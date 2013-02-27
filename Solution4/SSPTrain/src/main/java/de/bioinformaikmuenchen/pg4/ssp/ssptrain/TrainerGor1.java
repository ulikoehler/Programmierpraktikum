/*
 * GOR1 Trainer
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;

/**
 *
 * @author spoeri
 */
public class TrainerGor1 extends Trainer {

    public int[][][] matrix = new int[3][19][20];
    
    @Override
    public void trainMatrix() {
        
    }

    @Override
    public void parseAminoSeq(String id, String aminoSeq, String SecStruct) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
