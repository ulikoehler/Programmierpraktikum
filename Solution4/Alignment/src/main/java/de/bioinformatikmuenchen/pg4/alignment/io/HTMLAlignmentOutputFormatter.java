package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author koehleru
 */
public class HTMLAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    private static DecimalFormat numberFormat = getTwoDigitDecimalFormat();

    private static DecimalFormat getTwoDigitDecimalFormat() {
        DecimalFormat ret = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        ret.setDecimalFormatSymbols(dfs);
        ret.setGroupingUsed(false);
        ret.setMinimumFractionDigits(2);
        ret.setMaximumFractionDigits(2);
        return ret;
    }

    public String format(AlignmentResult result) {
        check(result);
        StringBuilder builder = new StringBuilder();
        builder.append("<div>");
        for (SequencePairAlignment align : result.getAlignments()) {
            builder.append("<h3>Aligment of ").append(result.getQuerySequenceId()).append(" and ").append(result.getTargetSequenceId()).append("</h3>");
            builder.append("<h4>Score: ").append(numberFormat.format(result.getScore())).append("</h4>");
            align.calculateMatchLine();
            for (int i = 0; i < align.queryAlignment.length(); i += 100) {
                //First line
                builder.append("<pre>");
                builder.append(align.queryAlignment.substring(i, Math.min(align.queryAlignment.length() - i, 100)));
                builder.append("</pre>");
                //Second line in red
                builder.append("<pre style=\"color: red;\">");
                builder.append(align.matchLine.substring(i, Math.min(align.matchLine.length() - i, 100)));
                builder.append("</pre>");
                //Third line
                builder.append("<pre>");
                builder.append(align.targetAlignment.substring(i, Math.min(align.targetAlignment.length() - i, 100)));
                builder.append("</pre><br/>");
            }

            builder.append("</div>");
        }
        return builder.toString();
    }
}