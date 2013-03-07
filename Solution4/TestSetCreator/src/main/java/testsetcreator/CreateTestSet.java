/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsetcreator;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentAlgorithm;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentMode;
import de.bioinformatikmuenchen.pg4.alignment.Gotoh;
import de.bioinformatikmuenchen.pg4.alignment.ParamTuning;
import de.bioinformatikmuenchen.pg4.alignment.gap.AffineGapCost;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 *
 * @author schoeffel
 */
public class CreateTestSet {

    public static void main(String[] args) throws IOException {
        /**
         * getting sequences that are to be scored for possible new test set
         */
        Splitter whitespaceSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults();
        ArrayList<TTuple> possibleset = new ArrayList<TTuple>();
        System.out.println("Parsing candidate");
        String filename = "little.txt";
        BufferedReader reader1 = new BufferedReader(new FileReader(filename));
        String line;
        String id;
        String sequence;
        while ((line = reader1.readLine()) != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '>') {
                    ArrayList<String> split = Lists.newArrayList(whitespaceSplitter.split(line));
                    id = split.get(1);
                    line = reader1.readLine();
                    split = Lists.newArrayList(whitespaceSplitter.split(line));
                    sequence = split.get(1);
                    possibleset.add(new TTuple(id, sequence, Double.NaN));
                }
            }
        }
        reader1.close();
        /**
         * finish sequence library
         */
        System.out.println("training");
        String training = "CBmod";
        ArrayList<String> cb513seq = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(training));

        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '>') {
                    line = reader.readLine();
                    ArrayList<String> split = Lists.newArrayList(whitespaceSplitter.split(line));
                    sequence = split.get(1);
                    cb513seq.add(sequence);
                }
            }
        }
        reader.close();
        System.out.println("finished parsing");
        /**
         * assign scores for each candidate sequence based on pairswise
         * alignments with trainingset
         */
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(ParamTuning.class.getResourceAsStream("/matrices/dayhoff.mat")));
        Gotoh gotoh = new Gotoh(AlignmentMode.LOCAL, AlignmentAlgorithm.GOTOH, matrix, new AffineGapCost(-12, -1));

        double number = possibleset.size();
        for (int i = 0; i < possibleset.size(); i++) {
            String sequence2 = possibleset.get(i).att2;
            double weigthedscore = 0;
            for (int j = 0; j < cb513seq.size(); j++) {
                String sequence1 = cb513seq.get(j);

                Sequence s1 = new Sequence(sequence1);
                Sequence s2 = new Sequence(sequence2);
                AlignmentResult result = gotoh.align(s1, s2);
                double score = result.getScore();
                weigthedscore += Math.pow(score, 3);
            }
            weigthedscore = Math.pow((weigthedscore / number), 1.0 / 3.0);
            possibleset.get(i).set3(weigthedscore);
        }

        /**
         * writing ne list for candidate sequences that pass thresholds
         */
        System.out.println("Creating output list");
        double minscore = 100;
        double maxscore = 130;

        ArrayList<ZTuple> testsetlist = new ArrayList<ZTuple>();

        for (int i = 0; i < possibleset.size(); i++) {
            if (possibleset.get(i).att3 >= minscore && possibleset.get(i).att3 <= maxscore) {
                testsetlist.add(new ZTuple(possibleset.get(i).att1, possibleset.get(i).att2));
            }
        }
        /**
         * writing ne testsetfile
         */
        System.out.println("Writing output");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < testsetlist.size(); i++) {
            builder.append(">").append(testsetlist.get(i).att1).append("\n").append("AS ").append(testsetlist.get(i).att2).append("\n\n");
        }
        String pew = builder.toString();
        FileWriter out = new FileWriter("testset.txt");
        BufferedWriter output = new BufferedWriter(out);
        output.write(pew);
        output.close();
    }
}
