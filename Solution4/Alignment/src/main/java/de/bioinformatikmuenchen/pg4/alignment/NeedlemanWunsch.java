package alignment;

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
}
