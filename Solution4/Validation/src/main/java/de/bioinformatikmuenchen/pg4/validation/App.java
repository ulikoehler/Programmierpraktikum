package de.bioinformatikmuenchen.pg4.validation;

/**
 *
 *
 */
public class App {

    public static void main(String[] args) {
        String a1 = "VBSD--EFG";
        String b1 = "L-MNOPQR-";
        String c1 = "-VB-SDE-FG";
        String d1 = "LMNOP-QR--";

        String a2 = "QW-ERTA";
        String b2 = "--MORTA";
        String c2 = "QW--ERTA";
        String d2 = "-MOR--TA-";

        String a3 = "RO-FLMA-O";
        String b3 = "-GN_AALOL";
        String c3 = "ROFL-MAO";
        String d3 = "GNAALO-L";

        String a4 = "C-PT-LIVMVGLPARGKTYISKKLTRYLNFIGVPTREFNVGQYRRDMVKTYKSFEFFLPDNEEGLKIRKQCALAALNDVRKFLSEEGGHVAVFDATNTTRERRAMIFNFGEQNGYKTFFVESICVDPEVIAANIVQVKLGSPDYVNRDSDEATEDFMRRIECYENSYESLDEEQDRDLSYIKIMDVGQSYVVNRVADHIQSRIVYYLMNIHVTPR";
        String b4 = "CSPTMVI-MVGLPARGKTYISTKLTRYLNFIGTPTKVFNLGQYRREAV-SYKNYEFFLPDNMEALQIRKQCALAALKDVHNYLSHEEGHVAVFDATNTTRERRSLILQFAKEHGYKVFFIESICNDPGIIAENIRQVKLGSPDYIDCDREKVLEDFLKRIECYEVNYQPLDEELDSHLSYIKIFDVGTRYMVNRVQDHIQSRTVYYLMNIHVTPR";
        String c4 = "-CPTLIVMVGLPARGKTYISKKLTRYLNFIGVPTREFNVGQYRRDMVKTYKSFEFFLPDNEEGLKIRKQCALAALNDVRKFLSEEGGHVAVFDATNTTRERRAMIFNFGEQNGYKTFFVESICVDPEVIAANIVQVKLGSPDYVNRDSDEATEDFMRRIECYENSYESLDEEQDRDLSYIKIMDVGQSYVVNRVADHIQSRIVYYLMNIHVTPR";
        String d4 = "NSPTMVIMVGLPARGKTYISTKLTRYLNFIGTPTKVFNLGQYRREAV-SYKNYEFFLPDNMEALQIRKQCALAALKDVHNYLSHEEGHVAVFDATNTTRERRSLILQFAKEHGYKVFFIESICNDPGIIAENIRQVKLGSPDYIDCDREKVLEDFLKRIECYEVNYQPLDEELDSHLSYIKIFDVGTRYMVNRVQDHIQSRTVYYLMNIHVTPR";

        AliValiAlg test = new AliValiAlg(a4, b4, c4, d4);

        //System.out.println("Speci " + test.getSpeci());
        //System.out.println("Sensi " + test.getSensi());
        //System.out.println("Cover " + test.getCover());
        //System.out.println("MeanS " + test.getMeanS());
        //System.out.println("Inver " + test.getInver());
        
        String a5 = "---CCCHHHHHHCCC---";
        String b5 = "CCCCCCHHCHCHCCCCCC";
        
        StrucValiAlg test2 = new StrucValiAlg (a5,b5);

        System.out.println("QH " + test2.getQH());
        System.out.println("QE " + test2.getQE());
        System.out.println("QC " + test2.getQC());
        System.out.println("Q3 " + test2.getQ3());
        
    }
}