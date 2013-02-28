package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.*;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.distance.*;
/**
 *
 * @author tobias
 */
public class BackTrackPaths {

    double[][] matrix;
    double[][] matrixIn;
    double[][] matrixDel;
    int xLength;
    int yLength;
    String querySequence;
    String targetSequence;
    boolean[][] left;
    boolean[][] leftTop;
    boolean[][] top;
    boolean[][] hasArrow;
    IGapCost gap;
    IDistanceMatrix distance;

    public BackTrackPaths(double[][] matrix, Sequence querySeq, Sequence targetSeq, IGapCost gap, IDistanceMatrix distance) {
        this.matrix = matrix;
        this.xLength = querySeq.getSequence().length();
        this.yLength = targetSeq.getSequence().length();
        this.querySequence = querySeq.getSequence();
        this.targetSequence = targetSeq.getSequence();
        this.gap = gap;
        this.distance = distance;
        //init the bool arrays with false:
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                left[x][y] = false;
                leftTop[x][y] = false;
                top[x][y] = false;
                hasArrow[x][y] = false;
            }
        }
    }
    
    public BackTrackPaths(double[][] matrixA, double[][] matrixIn, double[][] matrixDel, Sequence querySeq, Sequence targetSeq, IGapCost gap, IDistanceMatrix distance) {
        this.matrix = matrixA;
        this.matrixIn = matrixIn;
        this.matrixDel = matrixDel;
        this.xLength = querySeq.getSequence().length();
        this.yLength = targetSeq.getSequence().length();
        this.querySequence = querySeq.getSequence();
        this.targetSequence = targetSeq.getSequence();
        this.gap = gap;
        this.distance = distance;
        //init the bool arrays with false:
        for (int x = 0; x < xLength; x++) {
            for (int y = 0; y < yLength; y++) {
                left[x][y] = false;
                leftTop[x][y] = false;
                top[x][y] = false;
                hasArrow[x][y] = false;
            }
        }
    }

    public void pathsNeedlemanWunsch(int x, int y) {
        if (x == 0 && y == 0) {
            return;
        }
        if (x > 0 && matrix[x][y] == matrix[x - 1][y] + gap.getGapCost(1)) {
            left[x][y] = true;
            hasArrow[x][y] = true;
            this.pathsNeedlemanWunsch(x - 1, y);
        }
        if (y > 0 && matrix[x][y] == matrix[x][y - 1] + gap.getGapCost(1)) {
            top[x][y] = true;
            hasArrow[x][y] = true;
            this.pathsNeedlemanWunsch(x, y - 1);
        }
        char A = querySequence.charAt(x); char B = targetSequence.charAt(y);
        if (x > 0 && y > 0) {
            if (matrix[x][y] == matrix[x - 1][y - 1] + distance.distance(A, B)) {
                leftTop[x][y] = true;
                hasArrow[x][y] = true;
                this.pathsNeedlemanWunsch(x - 1, y - 1);
            }
        }
    }
    
    public void pathsGotoh(int x, int y){//unfinished, since not neccessary anymore
        assert ((matrixIn != null) && (matrixDel != null));
        if (x == 0 && y == 0) {
            return;
        }
        if (x > 0 && matrix[x][y] == matrixIn[x][y]) {
            int shift = findK(matrix[x][y], x, y, true);
            left[x][y] = true;
            hasArrow[x][y] = true;
            this.pathsNeedlemanWunsch(x - 1, y);
        }
    }
    
    private int findK(double entry, int x, int y, boolean insertion){
        int shift = 0;
        if(insertion){
            while(x != 0){
                if((matrix[x-1][y] + gap.getGapCost(shift+1)) == entry){
                    shift++;
                    break;
                }
                else{
                    shift++; x--;
                }
            }
        }
        else{//Deletion
            while(x != 0){
                if((matrix[x][y-1] + gap.getGapCost(shift+1)) == entry){
                    shift++; break;
                }
                else{
                    shift++; y--;
                }
            }
        }
        assert shift > 0;
        return shift;
    }
    
    public int[] findMaxEntry(){
        int x = -1; int y = -1; double maxCellEntry = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if(matrix[i][j] >= maxCellEntry){
                    x = i; y = j; maxCellEntry = matrix[i][j];
                }
            }
        }
        assert ((x>=0) && (y>=0) && (maxCellEntry > Double.NEGATIVE_INFINITY));
        return new int[]{x,y};
    }
}
