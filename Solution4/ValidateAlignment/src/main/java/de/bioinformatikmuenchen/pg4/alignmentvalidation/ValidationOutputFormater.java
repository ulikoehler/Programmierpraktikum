/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;
import java.io.File;

/**
 *
 * @author schoeffel
 */
public class ValidationOutputFormater {
    
    private Detailed detail;
    private Summary summary;
    private ValidateOutputFormat format;
    private File detailfile;
    private File summaryfile;
    
    
    public ValidationOutputFormater(Detailed detai,Summary summa, ValidateOutputFormat form, File deta, File summ){        
        detail = detai;
        summary = summa;
        format = form;
        detailfile = deta;
        summaryfile = summ;
        
    }
    
    public void print(){        
        if(format == ValidateOutputFormat.HTML){
            HtmlOutputFormater out = new HtmlOutputFormater();
            out.print(detail, detailfile);
            out.print(summary, summaryfile);
        }else{
            TxtOutputFormater out = new TxtOutputFormater();
            out.print(detail, detailfile);
            out.print(summary, summaryfile);
        }       
    }
}
