package de.bioinformatikmuenchen.pg4.validation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String a1 ="VBSD__EFG";
        String b1 ="L_MNORQR_";
        String c1 ="_VB_SDE_FG";
        String d1 ="LMNOP_QR__";
        
        String a2 ="QW_ERTA";
        String b2 ="__MORTA";
        String c2 ="QW__ERTA";
        String d2 ="_MOR__TA_";
        
        AliValiAlg test = new AliValiAlg(a2,b2,c2,d2);
        
        System.out.println(test.getCover());
        
    }
}
