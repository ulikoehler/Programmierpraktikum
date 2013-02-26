package de.bioinformaikmuenchen.pg4.common.alignment;

/**
 * An alignment of exactly two sequences
 *
 * @author koehleru
 */
public class SequencePairAlignment {

    public String queryAlignment;
    public String targetAlignment;
    //'|' when two elems match, ' ' else
    private String matchLine;

    public SequencePairAlignment() {
    }

    public void calculateMatchLine() {
        matchLine = "";
        for (int i = 0; i < 10; i++) {
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
}
