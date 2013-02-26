package de.bioinformaikmuenchen.pg4.common.alignment;

/**
 * An alignment of exactly two sequences
 * @author koehleru
 */
public class SequencePairAlignment {
    private String queryAlignment;
    private String targetAlignment;

    public SequencePairAlignment(String queryAlignment, String targetAlignment) {
        this.queryAlignment = queryAlignment;
        this.targetAlignment = targetAlignment;
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
