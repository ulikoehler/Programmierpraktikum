/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter.DPMatrixInfo;
import java.io.IOException;

/**
 *
 * @author koehleru
 */
public interface IDPMatrixExporter {

    void write(DPMatrixInfo info) throws IOException;
    
}
