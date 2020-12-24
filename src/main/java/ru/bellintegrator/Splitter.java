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

    public static int splitFileToNumberOfFiles(String inputFile, String outputDir, Integer numberOfFiles) {
        return splitFileToNumberOfFiles(inputFile, outputDir, numberOfFiles, true);
    }

    public static int splitFileToNumberOfFiles(String inputFile, String outputDir, Integer numberOfFiles, boolean isHeader) {
        int numOfLines = 0;
        int capacity = 0;
        String oneLine;
        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(inputFile).toFile()))) {
            while (br.readLine() != null) {
                numOfLines++;
            }
            if (numOfLines % numberOfFiles != 0) capacity = (numOfLines/numberOfFiles) + 1;
            else capacity = numOfLines/numberOfFiles;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitFile(inputFile, outputDir, capacity, isHeader);
    }
}
