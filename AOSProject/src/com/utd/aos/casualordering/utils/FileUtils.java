package com.utd.aos.casualordering.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Subbu on 10/1/15.
 */
public class FileUtils {

    public static void readFile(String fileName) {
        System.out.println("readFile() entry");

        File output = new File(fileName);
        try (FileReader reader = new FileReader(output)) {
            for (int i = 0; i < 10; i++) {
                System.out.println("Reading " + reader.read());
            }
        } catch (Exception e) {
            System.err.println("Exception in readFile() method" + e.getLocalizedMessage());
        }

        System.out.println("readFile() exit");
    }

    public static void writeFile(String fileName) {
        System.out.println("writeFile() entry");

        File input = new File(fileName);
        try (FileWriter writer = new FileWriter(input);) {
            for (int i = 0; i < 10; i++) {
                System.out.println("Writing " + i);
                writer.write(i);
            }
        } catch (Exception e) {
            System.err.println("Exception in writeFile() method"+ e.getLocalizedMessage());
        }

        System.out.println("writeFile() exit");
    }
}
