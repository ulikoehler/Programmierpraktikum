package de.bioinformatikmuenchen.pg4.common.alignment;

/**
 * An alignment of exactly two sequences
 *
 * @author koehleru
 */
public class SequencePairAlignment {

    public String queryAlignment;
    public String targetAlignment;
    //'|' when two elems match, ' ' else
    public String matchLine;

    public SequencePairAlignment() {
    }

    public void calculateMatchLine() {
        matchLine = "";
        for (int i = 0; i < queryAlignment.length(); i++) {
            matchLine += (queryAlignment.charAt(i) == targetAlignment.charAt(i) ? '|' : ' ');
        }
    }

    public SequencePairAlignment(String queryAlignment, String targetAlignment) {
        this.queryAlignment = queryAlignment;
        this.targetAlignment = targetAlignment;
        assert queryAlignment.length() == targetAlignment.length();

    }

    public String getQueryAlignment() {
        return queryAlignment;
    }

    public void setQueryAlignment(String queryAlignment) {
        this.queryAlignment = queryAlignment;
    }

    public String getTargetAlignment() {
        return targetAlignment;
    }

    public void setTargetAlignment(String targetAlignment) {
        this.targetAlignment = targetAlignment;
    }

    @Override
    public String toString() {
        calculateMatchLine();
        return queryAlignment + "\n" + matchLine + "\n" + targetAlignment;
    }

    public double getMismatchPercentage() {
        calculateMatchLine();
        int mismatchCount = 0;
        for (int i = 0; i < matchLine.length(); i++) {
            if (matchLine.charAt(i) != '|') {
                mismatchCount++;
            }
        }
        return mismatchCount * 100.0 / (double) matchLine.length();
    }
}
