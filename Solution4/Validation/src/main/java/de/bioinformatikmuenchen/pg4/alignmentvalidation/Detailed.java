/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import java.util.ArrayList;
/**
 *
 * @author schoeffel
 */
public class Detailed {
    
    public ArrayList<FTuple> results = new ArrayList();
    
    public void add(FTuple input){
        results.add(input);
    }
    
}
