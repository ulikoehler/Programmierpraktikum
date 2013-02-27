/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import de.bioinformatikmuenchen.pg4.common.Sequence;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author koehleru
 */
public class SwissprotSequenceSource implements ISequenceSource {
    public Sequence getSequence(String id) {
        try {
            //////////////////////
            //////////////////////
            //NOTE: Only the first sequence is being processed. All further sequences are being ignored silently!
            //////////////////////
            //////////////////////
            //Setup the retriever
            URL url = new URL("http://www.uniprot.org/uniprot/" + id + ".fasta");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000);
            urlConnection.setReadTimeout(1000);
            //Download the sequence lines (in FASTA format9
            List<String> lines = IOUtils.readLines(urlConnection.getInputStream());
            //Build the sequence (see note above) from the FASTA
            StringBuilder builder = new StringBuilder();
            for(String line : lines) {
                //Process FASTA headline
                if(line.startsWith(">")) {
                    //Skip headline
                    //break if this is the second headline <=> first seq has already been read
                    if(builder.length() == 0) {
                        continue;
                    } else {
                        break;
                    }
                }
                //Handle data lines
                builder.append(line);
            }
            return new Sequence(id, builder.toString());
        } catch (IOException ex) {throw new RuntimeException(ex);
        }
    }
}
