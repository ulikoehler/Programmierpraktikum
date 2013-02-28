/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentAlgorithm;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentMode;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentOutputFormat;
import de.bioinformatikmuenchen.pg4.alignment.NeedlemanWunsch;
import de.bioinformatikmuenchen.pg4.alignment.NeedlemanWunschTest;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.recursive.ZeroOneAlignmentMatrix;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import static org.mockito.Mockito.*;

/**
 *
 * @author koehleru
 */
public class DPMatrixExporterIT {

    public DPMatrixExporterIT() {
    }

    public static void main(String[] args) throws IOException {
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(DPMatrixExporterIT.class.getResourceAsStream("/matrices/dayhoff.mat")));
        NeedlemanWunsch instance = new NeedlemanWunsch(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, new ConstantGapCost(-5));
        instance.setFreeShift(true);
        Sequence seq1Obj = new Sequence("GAATTCAGTTA");
        Sequence seq2Obj = new Sequence("GGATCGA");
        instance.align(seq1Obj, seq2Obj);
        final DPMatrixExporter exp = new DPMatrixExporter(null, AlignmentOutputFormat.SCORES);
        //mockit    
        instance.writeMatrices(new IDPMatrixExporter() {
            @Override
            public void write(DPMatrixExporter.DPMatrixInfo info) throws IOException {
                System.out.println(info.toString());
                System.out.println();
                System.out.println(exp.formatMatrixHTML(info, true).toString());
            }
        });
    }

    public static void testSomeMethod() {
        DPMatrixExporter.DPMatrixInfo info;
        info = new DPMatrixExporter.DPMatrixInfo();
        info.topLeftArrows = new boolean[3][3];
        info.topArrows = new boolean[3][3];
        info.leftArrows = new boolean[3][3];
        info.matrix = new double[3][3];
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                info.topLeftArrows[i][j] = rand.nextBoolean();
                info.leftArrows[i][j] = rand.nextBoolean();
                info.topArrows[i][j] = rand.nextBoolean();
                info.matrix[i][j] = rand.nextDouble();
            }
        }
        //Modify some values
        info.topLeftArrows[0][0] = false;
        info.leftArrows[0][0] = false;
        info.topArrows[0][0] = false;
        //Export it and stderr-print it
        // the unit test just checks for errors
        info.query = "ABC";
        info.queryId = "1ABC";
        info.target = "DEF";
        info.targetId = "1DEF";
        info.xSize = 3;
        info.ySize = 3;
        info.score = 3.1415;
        DPMatrixExporter instance = new DPMatrixExporter(null, AlignmentOutputFormat.HTML);
        System.out.println(instance.formatMatrixHTML(info, true));
    }
}