/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common;

/**
 *
 * @author koehleru
 */
public class Sequence {

    private String id;
    private String sequence;
    private String as;
    private String ss;

    public String getAs() {
        return as;
    }

    public String getSs() {
        return ss;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }    
    
    public Sequence(String sequence) {
        this.id = null;
        this.sequence = sequence;
    }

    public Sequence(String id, String sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    /**
     * @return The id, may be null
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sequence other = (Sequence) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.sequence == null) ? (other.sequence != null) : !this.sequence.equals(other.sequence)) {
            return false;
        }
        return true;
    }
}
