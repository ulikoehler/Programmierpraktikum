package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.Sequence;

/**
 * Alignment processor that proxies data to two inner processors. For results,
 * ap1 is always preferred
 *
 * @author koehleru
 */
public class AlignmentProcessorBenchmarkController<T1 extends AlignmentProcessor, T2 extends AlignmentProcessor> extends AlignmentProcessor {

    private T1 ap1;
    private T2 ap2;
    private long ap1AlignTime = 0;
    private long ap2AlignTime = 0;
    private boolean verbose = false;

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public String getAp1ClassName() {
        return ap1.getClass().getCanonicalName();
    }

    public String getAp2ClassName() {
        return ap2.getClass().getCanonicalName();
    }

    public long getAp1AlignTime() {
        return ap1AlignTime;
    }

    public long getAp2AlignTime() {
        return ap2AlignTime;
    }

    public AlignmentProcessorBenchmarkController(T1 ap1, T2 ap2) {
        this.ap1 = ap1;
        this.ap2 = ap2;
    }

    @Override
    public AlignmentResult align(Sequence seq1, Sequence seq2) {
        long before1 = System.currentTimeMillis();
        AlignmentResult result1 = ap1.align(seq1, seq2);
        long after1 = System.currentTimeMillis();
        if (verbose) {
            System.err.println("\tFinished " + getAp1ClassName() + " alignment");
        }
        long before2 = System.currentTimeMillis();
        AlignmentResult result2 = ap2.align(seq1, seq2);
        long after2 = System.currentTimeMillis();
        if (verbose) {
            System.err.println("\tFinished " + getAp2ClassName() + " alignment");
        }
        ap2AlignTime += (after2 - before2);
        ap1AlignTime += (after1 - before1);
        return result1;
    }

    @Override
    public void writeMatrices(DPMatrixExporter exporter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
