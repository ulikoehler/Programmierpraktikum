/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentOutputFormat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koehleru
 */
public class DPMatrixExporterTest {

    public DPMatrixExporterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        DPMatrixExporter.DPMatrixInfo info;
        info = new DPMatrixExporter.DPMatrixInfo();
        info.topLeftArrows = new boolean[3][3];
        info.topArrows = new boolean[3][3];
        info.leftArrows = new boolean[3][3];
        info.matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                info.topLeftArrows[i][j] = true;
                info.leftArrows[i][j] = true;
                info.topArrows[i][j] = true;
                info.matrix[i][j] = 0.0;
            }
        }
        //Add some matrix value
        info.matrix[1][2] = 3.0;
        info.matrix[1][1] = 1.1;
        info.matrix[2][0] = 1.7;
        info.query = "ABC";
        info.queryId = "AId";
        info.target = "DEF";
        info.targetId = "DId";
        info.xSize = 3;
        info.ySize = 3;
        DPMatrixExporter instance = new DPMatrixExporter(null, AlignmentOutputFormat.HTML);
        System.out.println(instance.formatMatrixHTML(info, true));
    }
}