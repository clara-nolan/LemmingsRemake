package org.cis.lemmings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {
    private BufferedReader reader;

    public CsvReader(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<String[]> getCsvData() throws IOException {
        ArrayList<String[]> data = new ArrayList<String[]>();
        while (reader.ready()) {
            String line = reader.readLine();
            data.add(line.split(",", 0));
        }
        return data;
    }
}
