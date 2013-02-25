/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformaikmuenchen.pg4.common.Sequence;

/**
 * Alignment processor that proxies data to two inner processors.
 * For results, ap1 is always preferred
 * @author koehleru
 */
public class AlignmentProcessorBenchmarkController<T1 extends AlignmentProcessor, T2 extends AlignmentProcessor> extends AlignmentProcessor {
    private T1 ap1;
    private T2 ap2;
    private long t1AlignTime = 0;
    private long t2AlignTime = 0;

    public AlignmentProcessorBenchmarkController(T1 ap1, T2 ap2) {
        this.ap1 = ap1;
        this.ap2 = ap2;
    }

    @Override
    public AlignmentResult align(Sequence seq1, Sequence seq2) {
        long before1 = System.currentTimeMillis();
        AlignmentResult result = ap1.align(seq1, seq2);
        long after1 = System.currentTimeMillis();
        long before2 = System.currentTimeMillis();
        AlignmentResult result2 = ap2.align(seq1, seq2);
        long after2 = System.currentTimeMillis();
        t2AlignTime = (after2 - before2);
        t1AlignTime = (after1 - before1);
        return result;
    }    
}
