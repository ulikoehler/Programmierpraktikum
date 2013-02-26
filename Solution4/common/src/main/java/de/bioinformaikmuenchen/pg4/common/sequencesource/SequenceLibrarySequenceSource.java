/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import com.google.common.collect.Maps;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 *
 * @author koehleru
 */
public class SequenceLibrarySequenceSource implements ISequenceSource {

    private HashMap<String, String> sequences = Maps.newHashMap();

    public SequenceLibrarySequenceSource(String inputFile) throws IOException {
        this(new FileReader(inputFile));
    }

    /**
     * Factorize a new SequenceLibrarySequenceSource file from a seqlib file
     *
     * @param fileReader The reader to read data from -- closed when finished!
     */
    public SequenceLibrarySequenceSource(Reader inputReader) throws IOException {
        BufferedReader reader = new BufferedReader(inputReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String key = line.substring(0, line.indexOf(':'));
            String value = line.substring(line.indexOf(':') + 1);
            sequences.put(key, value);
        }
        reader.close();
    }

    public Sequence getSequence(String id) {
        return new Sequence(id, sequences.get(id));
    }
}
