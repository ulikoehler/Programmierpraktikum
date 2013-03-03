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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * An exporter for dynamic prorgamming matrices
 *
 * @author koehleru
 */
public class DPMatrixExporter implements IDPMatrixExporter {

    public static class DPMatrixInfo {

        public String query;
        public String queryId;
        public String target;
        public String targetId;
        public int xSize;
        public int ySize;
        public String matrixPostfix; //A filename postfix that specifies which matrix will be written
        public double score; //of the alignment
        public double[][] matrix;
        //'Arrows'
        public boolean[][] topLeftArrows;
        public boolean[][] leftArrows;
        public boolean[][] topArrows;
        //Paths that were taken by the algorithm
        public boolean[][] topLeftPath;
        public boolean[][] leftPath;
        public boolean[][] topPath;

        public DPMatrixInfo() {
        }

        @Override
        public String toString() {
            return "DPMatrixInfo{" + "query=" + query + ", queryId=" + queryId + ", target=" + target + ", targetId=" + targetId + ", xSize=" + xSize + ", ySize=" + ySize + ", matrixPostfix=" + matrixPostfix + ", score=" + score + ", matrix=" + matrix + ", topLeftArrows=" + topLeftArrows + ", leftArrows=" + leftArrows + ", topArrows=" + topArrows + '}';
        }
    }
    private AlignmentOutputFormat outputFormat; //HTML or 'something else'
    private File matrixDirectory = null;
    private static DecimalFormat numberFormat = getTwoDigitDecimalFormat();

    private static DecimalFormat getTwoDigitDecimalFormat() {
        DecimalFormat ret = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        ret.setDecimalFormatSymbols(dfs);
        ret.setMinimumFractionDigits(2);
        ret.setMaximumFractionDigits(2);
        return ret;
    }

    public DPMatrixExporter(File matrixDir, AlignmentOutputFormat outputFormat) {
        this.outputFormat = outputFormat;
        this.matrixDirectory = matrixDir;
    }

    @Override
    public void write(DPMatrixInfo info) throws IOException {
        //Build the filename
        String filename = info.queryId + "-" + info.targetId + (info.matrixPostfix == null ? "" : "-" + info.matrixPostfix);
        filename += (outputFormat == AlignmentOutputFormat.HTML ? ".html" : ".txt");
        //Open the file
        File outFile = new File(matrixDirectory, filename);
        Writer writer = new BufferedWriter(new FileWriter(outFile));
        if (outputFormat == AlignmentOutputFormat.HTML) {
            writer.append(formatMatrixHTML(info, true));
        } else { //Write plaintext
            writer.append(formatMatrixPlaintext(info));
        }
        //Generate the HTML
        writer.close();
    }

    /**
     * Generate a plaintext matrix representation and return the stringbuilder43
     */
    private StringBuilder formatMatrixPlaintext(DPMatrixInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append("\t\t");
        for (int x = 0; x < info.query.length(); x++) {
            builder.append(info.query.charAt(x)).append("\t");
        }
        builder.append("\n");
        for (int y = 0; y <= info.target.length(); y++) {
            builder.append(y == 0 ? ' ' : info.target.charAt(y - 1)).append("\t");
            for (int x = 0; x <= info.query.length(); x++) {
                builder.append(numberFormat.format(info.matrix[x][y])).append("\t");
            }
            builder.append("\n");
        }
        return builder;
    }

    public StringBuilder formatMatrixHTML(DPMatrixInfo info, boolean writeHead) {
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
            builder.append("<meta name=\"viewport\" content=\"width=device-width;minimum-scale=0.5,maximum-scale=1.0; user-scalable=1;\" />");
            builder.append("<style type=\"text/css\">");
            builder.append(".container {border: thin;border-style:solid;width:80px !important;height:80px !important;position:relative;display:inline-block;}\n");
            builder.append(".matrix-element {font-size:130%;text-align: center;display:table-cell;vertical-align: middle;}\n");
            builder.append(".vcenter-container {display:table}\n");
            builder.append(".overlay-top, .overlay-left, .overlay-topleft, .vcenter-container {width: 80px;height: 80px; position: absolute;top: 0;left: 0;}\n");
            builder.append(".overlay-top {z-index: -2;}\n");
            builder.append(".overlay-left {z-index: -3;}\n");
            builder.append(".overlay-topleft {z-index: -4;}\n");
            builder.append(".line-container {display:inline-block;white-space:nowrap;}\n");
            builder.append("</style>");
            builder.append("</head><body>");
        }
        builder.append("<h3>").append("Alignment of:&nbsp;").append(info.queryId).append(" and ").append(info.targetId).append("</h3>");
        builder.append("<p>Score:&nbsp;").append(info.score).append("</p>");
        //Foreach field, write one <div>
        for (int y = 0; y < info.ySize; y++) {
            builder.append("<div class=\"line-container\">");
            for (int x = 0; x < info.xSize; x++) {
                builder.append("<div class=\"container\">");
                //Write
                builder.append("<div class=\"vcenter-container\">");
                builder.append("<div class=\"matrix-element\">");
                builder.append(numberFormat.format(info.matrix[x][y]));
                builder.append("</div>"); //Matrix element
                builder.append("</div>"); //vcenter-container
                //
                //Overlays
                //
                //Left
                if (info.leftPath[x][y]) {
                    builder.append("<img src=\"L.svg\" class=\"overlay-left\" />");
                } else if (info.leftArrows[x][y]) {
                    builder.append("<img src=\"B-L.svg\" class=\"overlay-left\" />");
                }
                //Top
                if (info.topPath[x][y]) {
                    builder.append("<img src=\"T.svg\" class=\"overlay-top\" />");
                } else if (info.topArrows[x][y]) {
                    builder.append("<img src=\"B-T.svg\" class=\"overlay-top\" />");
                }
                //Top left
                if (info.topLeftPath[x][y]) {
                    builder.append("<img src=\"LT.svg\" class=\"overlay-topleft\" />");
                } else if (info.topLeftArrows[x][y]) {
                    builder.append("<img src=\"B-LT.svg\" class=\"overlay-topleft\" />");
                }
                //
                //End overlays
                //
                builder.append("</div>"); //container
            }
            builder.append("</div>");
            builder.append("<br/>\n");
        }
        //Write footer
        if (writeHead) {
            builder.append("</body></html>");
        }
        return builder;
    }
}
