package DNA_zu_Protein;
import java.util.HashMap;

public class DNA_Protein_homo_sapiens {

    private int me;


  public static void main(String[] args) {
        /*String DNA;
      DNA = "AAAT";
      //DNA = "GGCAGATGCCCTAGTTTCCCTTCATGTTCCTCTAGCCGATGATGATG";
      //DNA = "ATGCTCATCCACATGTGGATGAGGGAAGGTCGTGTGCTGTAGTCCATAATGGAATTATAGAAAATTTCAAAGAGTTGCGACGCGAGCTGACTGCGCAAGGCATTTCATTTGCTTCAGATACCGATTCAGAAATTATTGTTCAGCTGTTTTCTCTATATTATCAAGAGTCCCAAGATCTTGTGTTCAGCTTTTGTCAGACTCTAGCTCAACTCCGAGGTAGCGTAGCCTGCGCTTTGATTCATAAAGATCATCCTCATACGATTCTTTGCGCTTCTCAAGAGAGCCCTTTAATTCTTGGTTTAGGGAAAGAAGAGACGTTTATTGCTTCAGATTCGCGAGCTTTCTTCAAATATACTCGACATTCTCAAGCCTTGGCCTCCGGAGAATTTGCTATAGTTTCTCAAGGGAAAGAACCTGAGGTTTATAATTTGGAGCTTAAGAAAATCCATAAGGATGTACGACAAATCACCTGTAGTGAAGATGCTTCGGATAAAAGTGGCTACGGCTATTATATGCTGAAGGAAATCTATGATCAGCCAGAAGTTTTAGAAGGTCTGATTCAAAAACATATGGATGAAGAAGGACATATTTTATCTGAATTTTTATCAGATGTTCCTATCAAGAGTTTTAAAGAAATCACGATTGTTGCTTGCGGGTCTTCCTATCATGCTGGTTATCTCGCTAAATATATTATAGAGTCCTTAGTTTCAACTCCTGTACATATTGAAGTGGCTTCCGAATTTCGCTATCGACGTCCCTACATAGGTAAAGATACTTTGGGGATTTTGATCAGTCAATCAGGAGAAACAGCTGATACCCTAGCTGCTTTGAAGGAATTACGTCGCAGAAACATTGCTTATCTCCTAGGCATTTGCAATGTCCCGGAATCAGCAATTGCTCTTGGTGTGGATCACTGTCTGTTTTTAGAAGCGGGGGTGGAAATCGGTGTAGCTACGACAAAGGCTTTTACCTCGCAACTCTTGTTGCTTGTGTTTTTGGGTTTGAAATTAGCAAATGTACATGGTGCCTTGACTCACGCAGAACAATGTTCCTTTGGCCAGGGATTACAAAGCTTACCAGATCTCTGTCAAAAACTTCTTGCCAACGAGTCTCTCCATTCTTGGGCGCAGCCTTACTCCTATGAAGATAAGTTTCTTTTTCTAGGCCGTAGGTTGATGTATCCGGTGGTTATGGAGGCTGCCCTCAAACTCAAAGAAATTGCTTATATTGAAGCGAATGCGTATCCTGGTGGAGAAATGAAACATGGGCCCATAGCTTTAATTAGCAAAGGTACCCCTGTTATTGCATTTTGCGGTGATGATATTGTCTATGAAAAGATGATAGGCAACATGATGGAGGTTAAAGCTCGTCATGCTCATGTGATTGCTATTGCTCCTGAATCTCGTGAAGATATCGCTGCAGTTTCTGATCAACAGATCTTTGTCCCAGATTGTCATTTTCTCGCTGCTCCTGTGTTATATACTATAGTTGGTCAAGTGATGGCATATGCTATGGCGTTAGCAAAAGGAATGGAGATTGACTGTCCCAGAAATCTTGCCAAGTCTGTTACTGTAGAGTAAGTACTTCACAGTAGTAGACCTCGCAATCCTATGATGAAACTCTTTAGAACTTGTCTTAAGAGTGCATATGAAATTTAGGTATACGTTATGTCAAATAAAGTTCTAGGTGGGTCCTTGCTTATTGCAGGTTCTGCCATCGGTGCTGGTGTTTTAGCAGTTCCTGTATTGACCGCAAAAGGCGGTTTTTTCCCTGCAACTTTTCTCTATATTGTCTCCTGGCTTTTTTCTATGGCCTCAGGCCTTTGCCTTCTTGAAGTCATGACTTGGATGAAAGAATCAAAGAACCCAGTGAACATGCTTTCTATGGCGGAATCTATTTTAGGTCATGTAGGCAAGATTTCTATATGCCTTGTCTACTTGTTTCTCTTTTACTCCCTACTGATTGCCTATTTCTGCGAAGGGGGAAACATCTTATGCCGCGTGTTTAATTGTCAAAATTTAGGAATCTCATGGATTCGTCACCTTGGCCCTCTAGGTTTTGCTATATTGATGGGGCCTATCATTATGGCGGGAACAAAAGTGATTGATTACTGTAATCGTTTCTTTATGTTCGGCTTAACTGTAGCTTTTGGAATTTTCTGTGCCCTTGGATTTTTAAAAATCCAACCTAGCTTTCTGGTGCGTTCCTCATGGTTAACTACAATAAACGCATTTCCTGTGTTTTTCCTTGCTTTTGGATTCCAAAGTATCATTCCTACGTTGTACTACTACATGGACAAAAAAGTTGGAGATGTTAAAAAGGCAATTCTCATAGGAACGTTGATTCCTCTTGTTCTCTATGTCTTATGGGAAGTTGTGGTTTTAGGTGCTGTCTCTCTTCCGATTCTTTCCCAGGCTAAGATAGGTGGATATACTGCTGTAGAAGCTCTCAAGCAGGCCCATCGTTCTTGGGCATTTTATATTGCTGGAGAACTTTTTGGCTTCTTTGCTTTAGTCTCCTCTTTTGTAGGAGTTGCTCTCGGTGTTATGGACTTCCTGGCAGACGGTTTAAAATGGAATAAAAAATCACATCCATTTTCAATTTTCTTTTTAACATTTATTATTCCCCTTGCTTGGGCTGTTTGTTATCCTGAAATTGTTTTGACCTGTCTTAAGTATGCTGGGGGATTCGGGGCCGCCGTGATTATCGGGGTATTCCCAACATTGATTGTGTGGAAAGGGCGTTATGGCAAACAACATCACAGAGAGAAACAGTTAGTTCCAGGAGGAAAGTTTGCTTTATTTTTGATGTTCTTGTTGATAGTAATAAATGTAGTTAGCATTTATCATGAGCTTTAAATTTGTTTTGCTCTATTTTTCAATTTCAAGTGAGCAAATAGGAGGGTTCTATGGGACTATATGATCGTGACTATATACAAGATTCTCGAGTGCAGGGAACTTTTGCTTCAAGAGTCTATGGGTGGATGACAGCAGGGCTAATCGTAACTTCATGTGTTGCCCTGGGTCTTTATTTTTCTGGATTATACAGAAGTTTATTTTCTTTTTGGTGGGTGTGGTGTTTCGCTACGCTAGGCGTGTCTTTCTTTATCAACTCTAAAATCCAGACACTATCGGTTTCTGCTGTAGGGGGCCTTTTCCTTCTCTACTCAACATTAGAAGGAATGTTTTTTGGAACCTTACTTCCTGTCTACGCTGCTCAATATGGCGGAGGGGTGATCTGGGCCGCTTTTGGATCAGCAGCCTTGGTATTTGGCTTAGCAGCAGTATACGGAGCGTTTACAAAAAGCGATCTTACTAAAATTAGTAAGATTATGACTTTTGCTTTGATAGGACTTCTGCTAGTGACTCTAGTCTTTGCTGTGGTTTCGATGTTTGTATCTATGCCTTTAATCTACTTATTGATTTGCTATCTAGGGCTCGTCATCTTTGTTGGATTGACAGCTGCTGATGCGCAAGCAATTCGTCGGATTTCTTCTACTATAGGGGATAACAATACCTTGAGTTACAAACTCTCTTTGATGTTTGCTCTTAAGATGTATTGCAATGTCATCATGGTATTTTGGTATCTGCTGCAGATTTTCTCATCTTCAGGAAACCGAGACTAAACAACGACTTAGATCTTTTCTACTTCAGGAAATAATTTGTTTAGAAAAAGATCTAAGTCGAATTCATTAAGATCCTTTAAAGATTCCCCATAACCAATAAACTTTGTGGGGATTTTCAGTCGTTTAGCTATTTGAAATAGGGTGCCACCCTTGGCAGAGCCGTCTACTTTTGTGAAGATAAGACCAGAAAGGGGAACAACATCATGAAATACCCGCACCTGCTCTATAGCATTATTCCCTAAGGTAGAGTCCACAGTCATAAAAATTTCATGAGGAGCTCCCTCTAGAGCTTTGCCGCAAACCGAGACTATTTTGGAAAGCTCTTTCATAAGATTGCCATGTACATGCAGGCGACCTGAGGTATCAATAATGACTCTAGAGTACCCCCGGGCGATTGCAGATTGAATCCCATCAAAGGCAATAGCAGCAGCGTCCCCCCCGGGTTGTCCAGAGACAAAGCCACAGCCAAGTTCGTTGGCCCAGAGTCGTGCCTGGTCCATGCCAGCAGCTCGAAAGGTATCCGTGGCTACAAGCATGACGCTTTCAGATCGCTCTTTGTAGTAATGAGCAAGTTTGGCCGCTGTTGTCGTTTTTCCTGAGCCGTTGGTCCCAAGAAGTAAAGAAACAATCGGTCTGGTCTGGGAACTTTGTGAAGCTTGAGAAGGAAGACCTTCTAGAGACTCGCGAAGTAAAACTGTAATCAGATCTTTGATAGTAGATGCGTCAGCTTTTTTAGTCCGACGTAATCGTGCACACAATTCTTCAGTAAGCTCAGTACCAAAATCTGCTTCATAAAACAAACTTTCAGCATCTTCTATAAGATCTAAAGATATATTTTTTTTAAATAAAGATTGAAGCTTGTTTCTAAAGAATTTGAACATTGCTAATAG";
      String rDNA;
      String mRNA;
      int i = 0;
      int a = 0;
      DNA_Protein_homo_sapiens m = new DNA_Protein_homo_sapiens();
      mRNA =   m.transkription (DNA);
      int posStopCodon = -3;
      for(i=0;i<m.startCodonsZaehlen(mRNA);i++){
      int posNextStart = m.startCodonFinden(mRNA,posStopCodon+3);
      System.err.println("start " +posNextStart);
      posStopCodon = m.stopCodonFinden(mRNA, posNextStart+3);
      //System.err.println(posStopCodon);
      System.err.println("stop " +posStopCodon);
      if(posNextStart < 0 || posStopCodon < 0)
      break;
      m.translation2(mRNA, a, a);
      }

      rDNA = m.reverse(DNA);
      System.err.println(DNA);
      System.err.println(rDNA);
      mRNA = m.transkription(rDNA);
      for(i=0;i<m.startCodonsZaehlen(mRNA);i++){
      int posNextStart = m.startCodonFinden(mRNA,posStopCodon+3);
      System.out.println("start " +posNextStart);
      posStopCodon = m.stopCodonFinden(mRNA, posNextStart+3);
      //System.err.println(posStopCodon);
      System.out.println("stop " +posStopCodon);
      if(posNextStart < 0 || posStopCodon < 0)
      break;
      m.translation2(mRNA, a, a);
      }
      System.out.println("Compare:");
      m.compare(DNA, DNA);
      System.out.println(m.compare(DNA, DNA));

      System.out.println("Treffer:");
      String compare = m.compare(DNA, DNA);
      int counter=0;
      m.matches(compare, DNA, counter);
      System.out.println(m.matches(compare, DNA, counter));*/
       /*DNA_Protein_homo_sapiens m = new DNA_Protein_homo_sapiens();
        int Straffaktor=-2;
        int Matchfaktor=2;
        int Mismatchfaktor=0;
        String s="ABCBCBA";
        String t="ABCBA";
        m.aehnlichkeit(s, t,Straffaktor,Matchfaktor,Mismatchfaktor);
        int i=s.length();
        int j=t.length();
        int[][]M=m.aehnlichkeit_ohne_ausgabe(s, t,Straffaktor,Matchfaktor,Mismatchfaktor);

        DualString ds = m.opt_Alignment(i,j,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
        System.out.println(ds.s);
        System.out.println(ds.m);
        System.out.println(ds.t);
        //m.me = Straffaktor;
        DualString ds_2=m.opt_Alignment_2(i, j, M, s, t, Straffaktor, Matchfaktor, Mismatchfaktor);
        System.out.println(ds_2.s);
        System.out.println(ds_2.m);
        System.out.println(ds_2.t);

        DualString ds_3=m.opt_Alignment_3(i, j, M, s, t, Straffaktor, Matchfaktor, Mismatchfaktor);
        System.out.println(ds_3.s);
        System.out.println(ds_3.m);
        System.out.println(ds_3.t);*/
        DNA_Protein_homo_sapiens m = new DNA_Protein_homo_sapiens();
//        String DNA = "ATCATGTTTTAG";
//        String DNA = "ATCATGTTTTAGAAAATGATGATGATG";
//        String mRNA = m.transkription(DNA);
//        System.out.println(m.translation(mRNA));
//        m.gegenstrang(DNA);
//        m.splicing(DNA);
        m.baum();
        }


  
    

    public String transkription (String DNA) {
        String mRNA = new String();
            for (int i=0; i<DNA.length(); i=i+1) {
                char x = DNA.charAt (i);
                if (x=='T') {
                    mRNA += "U";
                }
                else if (x=='A' | x=='C'| x=='G') {
                    mRNA += x;
                }
            }
        System.out.println("mRNA:" + mRNA+"\n"+"     012345678901234567890123456789012");
        return mRNA;
    }
    String rcomp;
    public String gegenstrang(String DNA){
        this.rcomp = "";
        String mRNA = this.transkription(DNA);
        String reverse_mRNA = "";
        for(int a=0;a<mRNA.length();a++){
            char tc = mRNA.charAt(a);
            if(tc=='A'){
                this.rcomp += "U";
            }
            else if(tc=='U'){
                this.rcomp += "A";
            }
            else if(tc=='C'){
                this.rcomp += "G";
            }
            else if(tc=='G'){
                 this.rcomp += "C";
         }
        }
        for(int i=mRNA.length()-1;i>-1;i--){
            char temp_char = mRNA.charAt(i);
            if(temp_char=='A'){
                reverse_mRNA += "U";
            }
            else if(temp_char=='U'){
                reverse_mRNA += "A";
            }
            else if(temp_char=='C'){
                 reverse_mRNA += "G";
            }
            else if(temp_char=='G'){
                 reverse_mRNA += "C";
         }
        }
        //reverse_mRNA += " 3'";
        System.out.println(this.rcomp);
        System.out.println(reverse_mRNA);
        return reverse_mRNA;
    }
    
    public int orf;         //Diese globalen Variablen werden für die Methode splicing(String DNA) verwendet.
    public String smRNA;
    
    public String splicing (String mRNA){
        this.smRNA = "";
        //String mRNA = this.transkription(DNA);
        int[] pos_sc = new int[mRNA.length()/3];
        int sc_zahler = 0;
        int stop_counter = 0;
        String spliced_mRNA = "";
         for(int i=0;i<mRNA.length()-2;i++){ //alle ATG finden
             if(mRNA.substring(i,i+3).equals("AUG")){
                 /*if(i>2){
                     if(mRNA.substring(i-2,i+3).equals("UAAUG")==false&&mRNA.substring(i-2,i+3).equals("UGAUG")==false){
                         pos_sc[sc_zahler] = i;//Positionen festhalten
                         sc_zahler++;//sc_zahler um 1 erhöhen
                     }//if Schleife sichert gegen Überschneidungen ab
                 }*/
                     //else{
                     pos_sc[sc_zahler] = i;//Positionen festhalten
                     sc_zahler++;//sc_zahler um 1 erhöhen
                     //}
             }
        }
        int [] pos_sc_kurz = new int[sc_zahler];
        for(int n=0;n<sc_zahler;n++){//pos_sc in kleineres array pos_sc_kurz umschreiben
            pos_sc_kurz[n] = pos_sc[n];
        }
        pos_sc = new int[sc_zahler];//pos_sc wird als array für die stop codon positionen verwendet
        for(int k=0;k<sc_zahler;k++){
            if(sc_zahler>1){//Falls es mehr als ein Startcodon gibt
                if(k<sc_zahler-1){//Falls das letzte Startcodon noch nicht gefunden wurde
                    try{
                        for(int l=pos_sc_kurz[k];l<pos_sc_kurz[k+1]+3;l=l+3){//versucht ein stopcodons zwischen zwei startcodons zu finden
                            if(l<mRNA.length()-3){
                                if(mRNA.substring(l, l+3).equals("UGA")||mRNA.substring(l, l+3).equals("UAA")||mRNA.substring(l, l+3).equals("UAG")){
                                    pos_sc[k] = l;
                                    stop_counter++;
                                    break;
                                }
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e){}
                }
                    else{//Wenn hinter dem letzten Startcodon ein Stopcodon gesucht wird
                        for(int p=pos_sc_kurz[k];p<mRNA.length();p=p+3){
                            try{
                                if(mRNA.substring(p, p+3).equals("UGA")||mRNA.substring(p, p+3).equals("UAA")||mRNA.substring(p, p+3).equals("UAG")){
                                        pos_sc[k] = p;
                                        stop_counter++;
                                        break;
                                }
                            }
                            catch (StringIndexOutOfBoundsException e2){}
                        }
                }
                int c1 = 0;
                int c2 = 0;
                for(int q=0;q<sc_zahler;q++){
                    if(pos_sc[q]!=0){c1++;}
                    if(pos_sc_kurz[q]!=0){c2++;}
                }
                if(pos_sc[k]-pos_sc_kurz[k]+3>0&&pos_sc[k]>=pos_sc_kurz[k]+6 && 
                        pos_sc[k]!=0 && 
                        mRNA.substring(pos_sc_kurz[k], pos_sc_kurz[k]+3).equals("ATG")==false
                   )
                {//&&c1==c2
                    spliced_mRNA += mRNA.substring(pos_sc_kurz[k], pos_sc[k]+3);
                }
            }
            else if(sc_zahler==0){System.out.println("Keine Startcodons!");}//kein Startcodon
            else if(sc_zahler==1){//genau ein Startcodon
                int stop = 0;
                for(int o=pos_sc_kurz[0];o<mRNA.length();o=o+3){
                    if(o<mRNA.length()-2){
                    if(mRNA.substring(o, o+3).equals("UGA")||mRNA.substring(o, o+3).equals("UAA")||mRNA.substring(o, o+3).equals("UAG")){
                        if(mRNA.substring(o-1,o+3).equals("AUGA")==false){//sichert gegen Überschneidungen ab.
                        stop = o+3;
                        stop_counter++;
                        break;
                        }
                    }
                }
                    else{break;}
                }
                if(stop<pos_sc_kurz[0]){stop=pos_sc_kurz[0];}
                spliced_mRNA = mRNA.substring(pos_sc_kurz[0], stop);
            }
        
    }//Hier beginnt der Ausgabeteil:
        System.out.println(spliced_mRNA);
        String protein = this.translation(spliced_mRNA);
        System.out.println(protein);
        this.orf = stop_counter;//sc_zahler;
        this.smRNA = spliced_mRNA;
        return protein;
    }

    public String translation (String mRNA) {
        HashMap <String, String> h=new HashMap <String, String>();
        h.put("UUU", "Phe");
        h.put("UUC", "Phe");
        h.put("UUA", "Leu");
        h.put("UUG", "Leu");
        h.put("CUU", "Leu");
        h.put("CUC", "Leu");
        h.put("CUA", "Leu");
        h.put("CUG", "Leu");
        h.put("AUU", "Ile");
        h.put("AUC", "Ile");
        h.put("AUA", "Ile");
        h.put("AUG", "Met");
        h.put("GUU", "Val");
        h.put("GUC", "Val");
        h.put("GUA", "Val");
        h.put("GUG", "Val");
        h.put("UCU", "Ser");
        h.put("UCC", "Ser");
        h.put("UCA", "Ser");
        h.put("UCG", "Ser");
        h.put("CCU", "Pro");
        h.put("CCC", "Pro");
        h.put("CCA", "Pro");
        h.put("CCG", "Pro");
        h.put("ACU", "Thr");
        h.put("ACC", "Thr");
        h.put("ACA", "Thr");
        h.put("ACG", "Thr");
        h.put("GCU", "Ala");
        h.put("GCC", "Ala");
        h.put("GCA", "Ala");
        h.put("GCG", "Ala");
        h.put("UAU", "Tyr");
        h.put("UAC", "Tyr");
        h.put("UAA", "Stop");
        h.put("UAG", "Stop");
        h.put("CAU", "His");
        h.put("CAC", "His");
        h.put("CAA", "Gln");
        h.put("CAG", "Gln");
        h.put("AAU", "Asn");
        h.put("AAC", "Asn");
        h.put("AAA", "Lys");
        h.put("AAG", "Lys");
        h.put("GAU", "Asp");
        h.put("GAC", "Asp");
        h.put("GAA", "Glu");
        h.put("GAG", "Glu");
        h.put("UGU", "Cys");
        h.put("UGC", "Cys");
        h.put("UGA", "Stop");
        h.put("UGG", "Trp");
        h.put("CGU", "Arg");
        h.put("CGG", "Arg");//hat gefehlt bzw. war CGC; wurde am 09.11.10 als CGG richtiggestellt, nachdem bei der Translation eine NullPointerException aufgetaucht war.
        h.put("CGA", "Arg");
        h.put("CGC", "Arg");
        h.put("AGU", "Ser");
        h.put("AGC", "Ser");
        h.put("AGA", "Arg");
        h.put("AGG", "Arg");
        h.put("GGU", "Gly");
        h.put("GGC", "Gly");
        h.put("GGA", "Gly");
        h.put("GGG", "Gly");

        String x = new String();
        String pr = new String();
        for(int i=0;i<mRNA.length();i=i+3){
            if(i<mRNA.length()-2){
                x = mRNA.substring(i, i+3);
                if(h.get(x).equals("Stop"))
                    pr += h.get(x)+" ";
                else
                    pr += h.get(x)+"  ";
            }
            else

                break;
        }
        return pr;
    }

    public String reverse(String DNA) {
        String ret = "";

        for(int i=0;i<DNA.length();i++){
            int n = -i-1;
            ret = ret + DNA.charAt(DNA.length()+n);

        }

        return ret;
    }

    public String compare(String DNA, String rDNA){
        String compare="";
        String dna="";
        String Rdna="";
        for(int i=0; i<DNA.length()||i<rDNA.length();i++){
            int n = i+1;
            int m = n+1;
            if(m>DNA.length()||m>rDNA.length()){
            if((DNA.substring(DNA.length()-1)).equals(rDNA.substring(rDNA.length()-1))){
                compare+=DNA.substring(DNA.length()-1);
            }
            else{
                compare+=" _ ";
            }
                break;
            }
            dna = DNA.substring(n-1,m-1);
            Rdna = rDNA.substring(n-1,m-1);
            if(dna.equals(Rdna)){
                compare = compare + dna;
            }
            else{
               compare+=" _";
               // dna = DNA.substring(n,m);
                //Rdna = rDNA.substring(n,m);

            }
        }
        return compare;

    }
    public int matches(String compare, String DNA, int counter){
        for(int i=0;i<compare.length();i++){
           if(compare.substring(i, i+1).equals("A")||compare.substring(i, i+1).equals("T")||compare.substring(i, i+1).equals("C")||compare.substring(i, i+1).equals("G")){
               counter++;
           }
        }
        return counter;
    }
    public String compareII (String DNA, String rDNA){
        String compareII="";
        String dna="";
        String Rdna="";
        for(int i=0;i<DNA.length()||i<rDNA.length();i++){
            int n=i+1;
            int m=n+1;
            dna=DNA.substring(n, m);
            Rdna=rDNA.substring(n, m);
            if(dna.equals(Rdna))
                compareII+=dna;
            else{
                int x=n+1;
                int y=x+1;
                for(i=0;i<DNA.length()-dna.length();i++){
                    if(y<=DNA.length()||y<=rDNA.length()){
                    String dnaNext=DNA.substring(x, y);
                    String RdnaNext=rDNA.substring(x, y);
                    if(dnaNext.equals(RdnaNext)){
                        compareII+=dnaNext;
                    x++;
                    y++;
                    }
                    else{
                    x++;
                    y++;
                }
            }
                }
                break;
        }
        }
        return compareII;

    
}
    public String alignment(String a, String b){
        a="ATCGACT";
        b="CTACGACATG";
        //Faktoren
        int Straffaktor=-5;
        int bonus=2;
        int missmatch=0;
        //Ende Faktoren
        //Initialisierung
        int matrix[][]=new int[a.length()][b.length()];
        int y=0;
        for(int i=1;i<matrix.length;i++){
            matrix[i][0]=i+Straffaktor;
        int j=i;
    int x=matrix[i-1][j]-Straffaktor;
    if(a.charAt(i)==b.charAt(j))
        y=matrix[i-1][j-1]+bonus;
    else{
        y=matrix[i-1][j-1]+missmatch;
        int z=Math.max(y, matrix[i][j-1]-Straffaktor);
        matrix[i][j]=Math.max(x, z);
        System.out.println(matrix[0]);
    }

        }
        for(int i=1;i<matrix[0].length;i++){
            matrix[0][i]=i+Straffaktor;
       }

        
        //Ende Initialisierung
      /*  int i=0;
        int y=0;
        int j=i;
    int x=matrix[i-1][j]-Straffaktor;
    if(a.charAt(i)==b.charAt(j))
        y=matrix[i-1][j-1]+bonus;
    else{
        y=matrix[i-1][j-1]+missmatch;
        int z=Math.max(y, matrix[i][j-1]-Straffaktor);
        matrix[i][j]=Math.max(x, z);
        System.out.println(matrix[i][j]);
    }*/

    
        //Matrix aufgefüllt
        return a;
    }
    public int[][] aehnlichkeit(String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        int[][] M=new int [s.length()+1][t.length()+1];
        // Initialisierung
        for(int i=0;i<=s.length();i++)
            for(int j=0;j<=t.length();j++)
                M[i][j]=0;
        // Initialisierung der Ränder
        for(int i=0;i<=s.length();i++)
            M[i][0]=i*Straffaktor;
        for(int j=0;j<=t.length();j++)
            M[0][j]=j*Straffaktor;
        // Auffüllen der Matrix
        for(int i=1;i<=s.length();i++)
            for(int j=1;j<=t.length();j++){
                int max=Math.max(M[i-1][j]+Straffaktor, M[i][j-1]+Straffaktor);
                if(j<t.length()||i<s.length()){
                if(s.substring(i-1, i).equals(t.substring(j-1, j))){
                     M[i][j]=Math.max(max, M[i-1][j-1]+Matchfaktor);
                }
                else
                     M[i][j]=Math.max(max, M[i-1][j-1]+Mismatchfaktor);
                }
                else{
                    if(s.substring(i-1).equals(t.substring(j-1))||s.substring(s.length()-1).equals(t.substring(t.length()-1)))
                         M[i][j]=Math.max(max, M[i-1][j-1]+Matchfaktor);
                    else
                         M[i][j]=Math.max(max, M[i-1][j-1]+Mismatchfaktor);
                }


            }
        // Ausgabe der aufgefüllten Matrix
        for(int i=0;i<=s.length();i++)
            for(int j=0;j<=t.length();j++){
                if(j<t.length()){
                    if(M[i][j]<0)
                        System.out.print(M[i][j]+"\t");
                    else
                System.out.print((M[i][j])+"\t\b");
                }
                else
                    
                System.out.println(M[i][j]+"\n\n\t\b");
            }
        return M;
    }
    //Erstellt die z
    public int[][] aehnlichkeit_ohne_ausgabe(String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        int[][] M=new int [s.length()+1][t.length()+1];
        // Initialisierung
        for(int i=0;i<=s.length();i++)
            for(int j=0;j<=t.length();j++)
                M[i][j]=0;
        // Initialisierung der Ränder
        for(int i=0;i<=s.length();i++)
            M[i][0]=i*Straffaktor;
        for(int j=0;j<=t.length();j++)
            M[0][j]=j*Straffaktor;
        // Auffüllen der Matrix
        for(int i=1;i<=s.length();i++)
            for(int j=1;j<=t.length();j++){
                int max=Math.max(M[i-1][j]+Straffaktor, M[i][j-1]+Straffaktor);
                if(j<t.length()||i<s.length()){
                if(s.substring(i-1, i).equals(t.substring(j-1, j))){
                     M[i][j]=Math.max(max, M[i-1][j-1]+Matchfaktor);
                }
                else
                     M[i][j]=Math.max(max, M[i-1][j-1]+Mismatchfaktor);
                }
                else{
                    if(s.substring(i-1).equals(t.substring(j-1))||s.substring(s.length()-1).equals(t.substring(t.length()-1)))
                         M[i][j]=Math.max(max, M[i-1][j-1]+Matchfaktor);
                    else
                         M[i][j]=Math.max(max, M[i-1][j-1]+Mismatchfaktor);
                }


            }
        return M;
    }
    //alle opt_Alignment Methoden führen das Traceback durch
    public DualString opt_Alignment(int i,int j,int[][]M,String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        String s_="";
        String t_="";
        String m_="";
        DualString tmp;
        
        if(i==0&&j==0){
            s_="";
            t_="";
            m_="";
        }
        //this.me
        else{
            if(M[i][j]==M[i-1][j]+Straffaktor){
                tmp =this.opt_Alignment(i-1, j,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1, i);
                t_=tmp.t+"-";
                m_=tmp.m+" ";
            }
            else if(M[i][j]==M[i][j-1]+Straffaktor){
                tmp=this.opt_Alignment(i, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+"-";
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+" ";
            }
            else
            {
                if(M[i][j]==M[i-1][j-1]+Matchfaktor){
                tmp=this.opt_Alignment(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1,i);
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+"|";
                }
                else{
                tmp=this.opt_Alignment(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1,i);
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+" ";
                }
            }
        }
       tmp=new DualString();
       tmp.s=s_;
       tmp.t=t_;
       tmp.m=m_;
       return tmp;
}
    public DualString opt_Alignment_2(int i,int j,int[][]M,String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        String s_="";
        String t_="";
        String m_="";
        DualString tmp;

        if(i==0&&j==0){
            s_="";
            t_="";
            m_="";
        }
        //this.me
        else {
                if((i>0&&j>0)&&(M[i][j]==M[i-1][j-1]+Matchfaktor||M[i][j]==M[i-1][j-1]+Mismatchfaktor)){
                    if(M[i][j]==M[i-1][j-1]+Matchfaktor){
                        tmp=this.opt_Alignment_2(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                        s_=tmp.s+s.substring(i-1,i);
                        t_=tmp.t+t.substring(j-1, j);
                        m_=tmp.m+"|";
                    }
                    else if(M[i][j]==M[i-1][j-1]+Mismatchfaktor) {
                        tmp=this.opt_Alignment_2(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                        s_=tmp.s+s.substring(i-1,i);
                        t_=tmp.t+t.substring(j-1, j);
                        m_=tmp.m+" ";
                    }
                }
                {
                    if(M[i][j]==M[i-1][j]+Straffaktor){
                        tmp =this.opt_Alignment_2(i-1, j,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                        s_=tmp.s+s.substring(i-1, i);
                        t_=tmp.t+"-";
                        m_=tmp.m+" ";
                    }
                    else if(M[i][j]==M[i][j-1]+Straffaktor){
                        tmp=this.opt_Alignment_2(i, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                        s_=tmp.s+"-";
                        t_=tmp.t+t.substring(j-1, j);
                        m_=tmp.m+" ";
                    }
        }
        }
       tmp=new DualString();
       tmp.s=s_;
       tmp.t=t_;
       tmp.m=m_;
       return tmp;

    
    }
     public DualString opt_Alignment_3(int i,int j,int[][]M,String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        String s_="";
        String t_="";
        String m_="";
        DualString tmp;

        if(i==0&&j==0){
            s_="";
            t_="";
            m_="";
        }
            if(i>1 && M[i][j]==M[i-1][j]+Straffaktor){
                tmp =this.opt_Alignment_3(i-1, j,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1, i);
                t_=tmp.t+"-";
                m_=tmp.m+" ";
            }
        System.out.println(s_);
        System.out.println(t_);
        
            if(j>1 && M[i][j]==M[i][j-1]+Straffaktor){
                tmp=this.opt_Alignment_3(i, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+"-";
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+" ";
            }
        System.out.println(s_);
        System.out.println(t_);
            
                if((i>0 && j>0) && M[i][j]==M[i-1][j-1]+Matchfaktor){
                tmp=this.opt_Alignment_3(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1,i);
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+"|";
                }
        System.out.println(s_);
        System.out.println(t_);
                if(i>0 && j>0 && M[i][j]==M[i-1][j-1]+Mismatchfaktor){
                tmp=this.opt_Alignment_3(i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                s_=tmp.s+s.substring(i-1,i);
                t_=tmp.t+t.substring(j-1, j);
                m_=tmp.m+" ";
                }

        System.out.println(s_);
        System.out.println(t_);
        
       tmp=new DualString();
       tmp.s=s_;
       tmp.t=t_;
       tmp.m=m_;
       return tmp;
    }
     
     public int[][] multiple_alignment(){
         
    int [][] m_a = new int [5][5];
    DNA_Protein_homo_sapiens m = new DNA_Protein_homo_sapiens();
    for(int i=0;i<5;i++)
        for(int j=0;j<5;j++){
            if(i==j)
                m_a[i][j] = 0;
        }
    String[] s = new String[5];
    s[0] = "ATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTAATGTA";
    s[1] = "TGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATGTGATG";
    s[2] = "TAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGATAGGA";
    s[3] = "AATGAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAAATGAA";
    s[4] = "TGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTGTGTTG";
    int strafe = -1;
    int match = 2;
    int mismatch = -2;
    int[][] a;
    for(int i=0;i<5;i++)
        for(int j=i+1;j<5;j++){
            a = m.aehnlichkeit_ohne_ausgabe(s[i],s[j],strafe, match, mismatch);
            m_a[i][j] = a[a.length-1][a[a.length-1].length-1];
        }
    String out_string = new String();
    out_string+=" A\tB\tC\tD\tE\t\t\n";
        for(int i=0;i<5;i++)
            for(int j=0;j<5;j++){
                if(j<4){
                    if(m_a[i][j]<0)
                        out_string+=(m_a[i][j]+"\t");
                    else
                        out_string+=((m_a[i][j])+"\t\b");
                }
                else
                    out_string+=(m_a[i][j]+"\n\n");
            }
    System.out.println(out_string);
    return m_a;
    }
     
     public String m_a_ausgabe(int[][] m_a, int counter){
         String out_string = new String();
             for(int i=0;i<counter;i++)
            for(int j=0;j<counter;j++){
                if(j<counter-1){
                    if(m_a[i][j]<0)
                        out_string+=(m_a[i][j]+"\t");
                    else
                        out_string+=((m_a[i][j])+"\t\b");
                }
                else
                    out_string+=(m_a[i][j]+"\n\n");
            }
    return out_string;
     }
     
     public void baum(){
         int[][] m_a = this.multiple_alignment();
         int[] ij = new int[2];
         int counter = m_a.length;//counter definiert die Anzahl der Taxa
         //größten Score finden:
         int max = -1000000;
         counter--;
         while(counter>=2){
             max = -1000000;
             for(int i=0;i<m_a.length;i++){
                 for(int j=0;j<m_a.length;j++){
                     int c = m_a[i][j];
                     if(c>max){//Falls der momentane Score c größer als max ist:
                         max = c;//setze max als c
                         ij[0] = i;//merke die positionen i und j in ij
                         ij[1] = j;
                     }
                 }
             }
             int[][]m_a_neu = new int[counter][counter];
             for(int i=0;i<counter;i++){
                 for(int j=0;j<counter;j++){
                     if(j==ij[0]){//falls sich j gleich der Zeile ist, in der in m_a der höchste score gefunden worden ist.
                         if(i==ij[0]){//falls  i==j
                             m_a_neu[i][j] = 0;
                         }
                         else{
                             if(i<ij[1]){
                                 m_a_neu[i][j] = (m_a[ij[0]][j]+m_a[ij[1]][j])/2;
                             }
                             else if(i>=ij[1]){
                                 m_a_neu[i][j] = (m_a[ij[0]][j+1]+m_a[ij[1]][j+1])/2;
                             }
                         }
                     }
                     if(i==ij[0]){//falls sich i gleich der Spalte ist, in der in m_a der höchste Score gefunden worden ist.
                         if(j==ij[0]){//falls  i==j
                             m_a_neu[i][j] = 0;
                         }
                         else{
                             if(j<ij[1]){
                                 m_a_neu[i][j] = (m_a[i][ij[0]]+m_a[i][ij[1]])/2;
                             }
                             else if(j>=ij[1]){
                                 m_a_neu[i][j] = (m_a[i+1][ij[0]]+m_a[i+1][ij[1]])/2;
                             }
                         }
                     }
                     if(i!=ij[0]&&j!=ij[0]){//falls i und j nicht in der Zeile bzw. Spalte des höchsten Score aus m_a sind.
                         if(i<ij[1]){//falls i sich vor der Spalte mit dem höchsten Score aus m_A befindet.
                             m_a_neu[i][j] = m_a[i][j];
                         }
                         else if(i>ij[1]){
                             m_a_neu[i][j] = m_a[i+1][j];
                         }
                         if(j<ij[1]){
                             m_a_neu[i][j] = m_a[i][j];
                         }
                         else if(j>ij[1]){
                             m_a_neu[i][j] = m_a[i][j+1];
                         }
                     }
                     /*if(j==ij[0]){
                         if(i==ij[0]&&j==ij[0]){
                             m_a_neu[i][j] = 0;
                         }
                     else{
                         m_a_neu[i][ij[0]] = (m_a[i][ij[0]]+m_a[i][ij[1]])/2;
                         }
                     }
                     else{
                         m_a_neu[i][j] = m_a[i][j];
                     }*/
                 }
             }
             //System.out.println Ausgabe
             System.out.println(this.m_a_ausgabe(m_a, counter));
             counter--;
             m_a = m_a_neu;
         }
     }
}




