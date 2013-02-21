/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DNA_zu_Protein;

/**
 *
 * @author admin
 */
public class DualString {
    public String s;
    public String t;
    public String m="";

    public String[] tmp_out(){
        String[] temp = new String[3];
        temp[0] = s;
        temp[1] = t;
        temp[2] = m;
        return temp;
    }
}