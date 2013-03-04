/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author koehleru
 */
public class SecStructDB {

    private HashMap<String, String> asMap = Maps.newHashMap();
    private HashMap<String, String> ssMap = Maps.newHashMap();

    public String getAS(String id) {
        return asMap.get(id);
    }

    public String getSS(String id) {
        return ssMap.get(id);
    }

    public SecStructDB(String filename) throws IOException {
        this(new FileInputStream(filename));
    }

    public SecStructDB(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        String currentSequenceId = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(">")) {
                line = line.substring(1).trim();
                currentSequenceId = line;
            } else if (line.startsWith("AS")) {
                line = line.substring(2).trim();
                asMap.put(currentSequenceId, line);
            } else if (line.startsWith("SS")) {
                line = line.substring(2).trim();
                ssMap.put(currentSequenceId, line);
            } else {
                System.err.println("Unkown line format: " + line);
            }
        }
        reader.close();
    }
}
