/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentOutputFormat;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * An exporter for dynamic prorgamming matrices
 *
 * @author koehleru
 */
public class DPMatrixExporter {

    public static class DPMatrixInfo {

        String query;
        String queryId;
        String target;
        String targetId;
        int xSize;
        int ySize;
        double[][] matrix;
        //'Arrows'
        boolean[][] topLeft;
        boolean[][] left;
        boolean[][] top;

        public DPMatrixInfo() {
        }

        public DPMatrixInfo(String query, String queryId, String target, String targetId, int xSize, int ySize, double[][] matrix, boolean[][] leftTop, boolean[][] left, boolean[][] top) {
            this.query = query;
            this.queryId = queryId;
            this.target = target;
            this.targetId = targetId;
            this.xSize = xSize;
            this.ySize = ySize;
            this.matrix = matrix;
            this.topLeft = leftTop;
            this.left = left;
            this.top = top;
        }
    }
    private AlignmentOutputFormat outputFormat; //HTML or 'something else'
    private File matrixDirectory = null;

    public DPMatrixExporter(AlignmentOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * Generate a plaintext matrix representation and return the stringbuilder43
     */
    private static StringBuilder matrixToString(String querySeq, String targetSeq, double[][] matrix) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t");
        for (int x = 0; x < querySeq.length(); x++) {
            builder.append(querySeq.charAt(x)).append("\t");
        }
        builder.append("\n");
        for (int y = 0; y <= targetSeq.length(); y++) {
            builder.append(y == 0 ? ' ' : targetSeq.charAt(y - 1)).append("\t");
            for (int x = 0; x <= querySeq.length(); x++) {
                builder.append(matrix[x][y]).append("\t");
            }
            builder.append("\n");
        }
        return builder;
    }

    private void writeMatrixPlaintextToFile(AlignmentResult result, double[][] matrix) throws IOException {
        File outFile = new File(matrixDirectory, result.getQuerySequenceId() + "-" + result.getTargetSequenceId() + ".txt");
        Writer writer = new BufferedWriter(new FileWriter(outFile));
        writer.write(matrixToString(result.getQuerySequenceId(), result.getTargetSequenceId(), matrix).toString());
        writer.close();
    }

    private void writeMatrixHTMLToFile(AlignmentResult result, double[][] matrix) throws IOException {
        writeMatrixHTMLToFile(result, matrix, true);
    }

    private void writeMatrixHTMLToFile(AlignmentResult result, double[][] matrix, boolean writeHead) throws IOException {

        //Open the file
        File outFile = new File(matrixDirectory, result.getQuerySequenceId() + "-" + result.getTargetSequenceId() + ".txt");
        Writer writer = new BufferedWriter(new FileWriter(outFile));
        //Generate the HTML
        writer.close();
    }

    private StringBuilder formatHTMLMatrix(DPMatrixInfo info) {
        return formatHTMLMatrix(info, false);
    }

    public StringBuilder formatHTMLMatrix(DPMatrixInfo info, boolean writeHead) {
        //Extract 
        assert info != null;
        assert info.matrix != null;
        assert info.xSize > 0;
        assert info.matrix.length == info.xSize;
        assert info.ySize > 0;
        assert info.matrix[0].length == info.xSize;
        StringBuilder builder = new StringBuilder();
        if (writeHead) {
            builder.append("<html><head>");
            builder.append("<style type=\"text/css\">");
            builder.append(".container {border: thin;border-style:solid;width:80px !important;height:80px !important;position:relative;display:inline-block;}\n");
            builder.append(".matrix-element {font-size:130%;text-align: center;display:table-cell;vertical-align: middle;}\n");
            builder.append(".vcenter-container {display:table}\n");
            builder.append(".overlay-top, .overlay-left, .overlay-topleft, .vcenter-container {width: 80px;height: 80px; position: absolute;top: 0;left: 0;}\n");
            builder.append(".overlay-top {z-index: 2;}\n");
            builder.append(".overlay-left {z-index: 3;}\n");
            builder.append(".overlay-topleft {z-index: 4;}\n");
            builder.append("</style>");
            builder.append("</head><body>");
        }
        //Foreach field, write one <div>
        for (int y = 0; y < info.ySize; y++) {
            for (int x = 0; x < info.xSize; x++) {
                builder.append("<div class=\"container\">");
                //Write
                builder.append("<div class=\"vcenter-container\">");
                builder.append("<div class=\"matrix-element\">");
                builder.append(info.matrix[x][y]);
                builder.append("</div>"); //Matrix element
                builder.append("</div>"); //vcenter-container
                //
                //Overlays
                //
                if (info.left[x][y]) {
                    builder.append("<img src=\"L.svg\" class=\"overlay-left\" />");
                }
                if (info.top[x][y]) {
                    builder.append("<img src=\"T.svg\" class=\"overlay-top\" />");
                }
                if (info.topLeft[x][y]) {
                    builder.append("<img src=\"LT.svg\" class=\"overlay-topleft\" />");
                }
                //
                //End overlays
                //
                builder.append("</div>"); //container
            }
            builder.append("<br/>\n");
        }
        //Write footer
        if (writeHead) {
            builder.append("</body></html>");
        }
        return builder;
    }
}
