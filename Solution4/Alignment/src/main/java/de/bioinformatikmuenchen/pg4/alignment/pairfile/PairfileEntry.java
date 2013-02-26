/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.pairfile;

import java.util.List;

/**
 *
 * @author koehleru
 */
public class PairfileEntry {

    public String first;
    public String second;
    private String annotations; //Other stuff, might not be interesting

    public PairfileEntry(String first, String second, String annotations) {
        this.first = first;
        this.second = second;
        this.annotations = annotations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.first != null ? this.first.hashCode() : 0);
        hash = 23 * hash + (this.second != null ? this.second.hashCode() : 0);
        hash = 23 * hash + (this.annotations != null ? this.annotations.hashCode() : 0);
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
        final PairfileEntry other = (PairfileEntry) obj;
        if ((this.first == null) ? (other.first != null) : !this.first.equals(other.first)) {
            return false;
        }
        if ((this.second == null) ? (other.second != null) : !this.second.equals(other.second)) {
            return false;
        }
        if ((this.annotations == null) ? (other.annotations != null) : !this.annotations.equals(other.annotations)) {
            return false;
        }
        return true;
    }
}
