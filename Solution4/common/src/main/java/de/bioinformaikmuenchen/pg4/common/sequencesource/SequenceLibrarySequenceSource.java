/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import com.google.common.collect.Maps;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 *
 * @author koehleru
 */
public class SequenceLibrarySequenceSource implements ISequenceSource {

    private HashMap<String, String> sequences = Maps.newHashMap();

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
