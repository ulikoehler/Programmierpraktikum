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
}
