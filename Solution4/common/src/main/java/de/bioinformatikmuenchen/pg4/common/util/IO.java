/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

/**
 *
 * @author spoeri
 */
public class IO {

    public static String readLineFromStdIn() {
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            return b.readLine();
        } catch (IOException e) {
            System.err.println("ERROR Reading line from STDIN: " + e.getMessage());
            System.exit(1);
        }
        return "";
    }

    public static boolean isValidFilePathOrName(String filepath) {

        File f = new File(filepath);
        try {
            f.getCanonicalFile();
        } catch (IOException e) {
            return false;
        }
        return true;

    }

    public static boolean isReadableFileAndNoDir(String filepath) {
        // no valid files are also not readable
        if (!isValidFilePathOrName(filepath)) {
            return false;
        }
        File f = new File(filepath);
        return f.canRead() && !f.isDirectory();
    }

    public static String isExistingReadableFileOrQuit(String file, String errorMsg) {
        if(isReadableFileAndNoDir(file)) return file;
        System.err.println(errorMsg);
        System.exit(1);
        return null;
    }
}
