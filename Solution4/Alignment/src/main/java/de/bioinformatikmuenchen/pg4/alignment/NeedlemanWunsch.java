package de.bioinformatikmuenchen.pg4.alignment;

public class NeedlemanWunsch {

    private int[][] matrix;
    private int x,y;
    
    public NeedlemanWunsch(int x, int y){
        matrix = new int[x][y];
        this.x = x;
        this.y = y;
    }
    
    public void initMatrix(int gap){
        for (int i = 0; i < x; i++) {
            matrix[i][0] = i*gap;
        }
        for (int i = 0; i < y; i++) {
            matrix[0][i] = i*gap;
        }
    }
    
    public void fillMatrix(int gap, int match, int misMatch, String seq1, String seq2){
        for (int i = 1; i < x; i++) {
            for (int j = 1; j < y; j++) {
                if(seq1.charAt(i-1)==seq2.charAt(j-1)){
                    matrix[i][j] = Math.max(matrix[i-1][j-1]+match, Math.max(matrix[i-1][j]+gap, matrix[i][j-1]+gap));
                }
                else{
                    matrix[i][j] = Math.max(matrix[i-1][j-1]+misMatch, Math.max(matrix[i-1][j]+gap, matrix[i][j-1]+gap));
                }
            }
        }
    }
    
    public String printMatrix(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                stringBuilder.append(matrix[i][j]).append("\t");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    } 

    public void opt_Alignment(DualString tmp,int i,int j,int[][]M,String s, String t, int Straffaktor,int Matchfaktor,int Mismatchfaktor){
        String s_="";
        String t_="";
        String m_="";
        DualString tmp2;
        if(this.counter<this.compare_counter)//Hört auf Alignments auszugeben, wenn die vorgegebene Anzahl erreicht wird.
        {
            if(i==0&&j==0){
                this.counter++;
                System.out.println(tmp.s +"\n"+ tmp.m +"\n"+ tmp.t);
                this.out.setText(this.out.getText() + "### Alignment "+ counter + " ###\n" + tmp.s +"\n"+ tmp.m +"\n"+ tmp.t+"\n\n");
                return;
            }
            if(i>0 && M[i][j]==M[i-1][j]+Straffaktor){
                s_=s.substring(i-1, i)+tmp.s;
                t_="-"+tmp.t;
                m_=" "+tmp.m;
                tmp2=new DualString();
                tmp2.s=s_;
                tmp2.t=t_;
                tmp2.m=m_;

                //System.out.println("####################\n"+tmp2.s +"  "+i+"  "+j+"\n"+ tmp2.m +"\n"+ tmp2.t);
                this.opt_Alignment(tmp2,i-1, j,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
            }
            if(j>0 && M[i][j]==M[i][j-1]+Straffaktor){
                s_="-"+tmp.s;
                t_=t.substring(j-1, j)+tmp.t;
                m_=" "+tmp.m;
                tmp2=new DualString();
                tmp2.s=s_;
                tmp2.t=t_;
                tmp2.m=m_;
                //System.out.println("####################\n"+tmp2.s +"  "+i+"  "+j+"\n"+ tmp2.m +"\n"+ tmp2.t);
                this.opt_Alignment(tmp2,i, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
            }
            if(i>0&&j>0){
                if(M[i][j]==M[i-1][j-1]+Matchfaktor){
                    s_=s.substring(i-1,i)+tmp.s;
                    t_=t.substring(j-1, j)+tmp.t;
                    m_="|"+tmp.m;
                    tmp2=new DualString();
                    tmp2.s=s_;
                    tmp2.t=t_;
                    tmp2.m=m_;
                   //System.out.println("####################\n"+tmp2.s +"  "+i+"  "+j+"\n"+ tmp2.m +"\n"+ tmp2.t);
                    this.opt_Alignment(tmp2,i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                }
                else{
                    s_=s.substring(i-1,i)+tmp.s;
                    t_=t.substring(j-1, j)+tmp.t;
                    m_=" "+tmp.m;
                    tmp2=new DualString();
                    tmp2.s=s_;
                    tmp2.t=t_;
                    tmp2.m=m_;
                    //System.out.println("####################\n"+tmp2.s +"  "+i+"  "+j+"\n"+ tmp2.m +"\n"+ tmp2.t);
                    this.opt_Alignment(tmp2,i-1, j-1,M,s,t,Straffaktor,Matchfaktor,Mismatchfaktor);
                }
                }
        }
    }
    public static void main(String[] args) {
        String seq1 = "TATAAT";//vertikale
        String seq2 = "TTACGTAAGC";//horizontale
        int gap = -4;
        int match = 3;
        int mismatch = -2;
        NeedlemanWunsch alignment = new NeedlemanWunsch(seq1.length()+1, seq2.length()+1);
        alignment.initMatrix(gap);
        alignment.printMatrix();
        alignment.fillMatrix(gap, match, mismatch, seq1, seq2);
        alignment.printMatrix();
    }
    
    class DualString {
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
}
