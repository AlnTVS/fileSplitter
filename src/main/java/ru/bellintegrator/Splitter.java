package ru.bellintegrator;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Splitter {

    public static int splitFile(String inputFile, String outputDir, Integer capacity) {
        return splitFile(inputFile, outputDir, capacity, true);
    }

    public static int splitFile(String inputFile, String outputDir, Integer capacity, boolean isHeader) {
        int filesCount = 0;
        int lineCounter = 0;
        String[] strings = inputFile.split("[\\/\\\\]");
        String fullFileName = strings[strings.length - 1];
        String fileName = fullFileName.split("\\W")[0];
        String header = "";
        String oneLine;
        List<String> stringList = new ArrayList<>(capacity + 2);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            if (isHeader) {
                header = br.readLine();
                stringList.add(header);
            }
            while ((oneLine = br.readLine()) != null) {
                stringList.add(oneLine);
                lineCounter++;
                if (lineCounter >= capacity) {
                    FileWorker.writeFileByStringList(stringList, outputDir + "\\" + fileName + "_" + ++filesCount + ".csv");
                    stringList.clear();
                    if (isHeader) stringList.add(header);
                    lineCounter = 0;
                }
            }
            if (stringList.size() > 1)
                FileWorker.writeFileByStringList(stringList, outputDir + "\\" + fileName + "_" + ++filesCount + ".csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filesCount;
    }
}
