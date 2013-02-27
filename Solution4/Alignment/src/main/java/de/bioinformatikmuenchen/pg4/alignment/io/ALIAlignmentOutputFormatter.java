package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformaikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformaikmuenchen.pg4.common.util.CollectionUtil;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author koehleru
 */
public class ALIAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    private DecimalFormat numberFormat = new DecimalFormat();

    public ScoreOnlyAlignmentOutputFormatter() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        numberFormat.setMinimumFractionDigits(4);
        numberFormat.setMinimumFractionDigits(4);
        numberFormat.setDecimalFormatSymbols(dfs);
    }

    public String format(AlignmentResult result) {
        //
        // NOTE: This formatter currently only prints the first alignment, even if there are more
        //
        StringBuilder ret = new StringBuilder();
        ret.append(">" + result.getSeq1Id() + " " + result.getSeq2Id() + " " + numberFormat.format(result.getScore()) + "\n");
        SequencePairAlignment alignment = result.getFirstAlignment();
        ret.append(result.getSeq1Id() + ": " + alignment.getQueryAlignment());
        ret.append(result.getSeq2Id() + ": " + alignment.getTargetAlignment());
        return;
    }
}