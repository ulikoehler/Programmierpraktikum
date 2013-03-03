/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

import java.util.ArrayList;

/**
 *
 * @author schoeffel
 */
public class Detailed {
    public ArrayList<VTupel> results = new ArrayList();
    
    public void add(VTupel input){
        results.add(input);
    }
}
