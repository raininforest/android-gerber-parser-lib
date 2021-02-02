package com.github.raininforest.gerberparserlib.syntaxparser;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GerberFileReader {
    private static final Logger log = LogManager.getLogger();
    private final String filename;
    private final List<String> stringList;

    public GerberFileReader(String filename) {
        this.filename = filename;
        stringList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getStringList() throws FileNotFoundException {
        readGerberFile();
        return stringList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readGerberFile() throws FileNotFoundException {
        Path file = Paths.get(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            log.trace("File " + filename + " opened");
            String line;
            while ((line = reader.readLine()) != null) {
                log.trace(line);
                stringList.add(line);
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла " + filename + '!');
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }
}
