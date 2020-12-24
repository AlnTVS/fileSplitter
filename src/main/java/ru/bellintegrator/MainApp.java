package ru.bellintegrator;

public class MainApp {
    public static void main(String[] args) {
        //path to input CSV file which need to split
        String inputFile = "C:\\workspace\\poligon\\fileSplitter\\fileSplitter.csv";
        //path to output directory for files
        String outputDir = "C:\\workspace\\poligon\\fileSplitter\\out";
        //size of split
        Integer holdingCapacity = 45;

        Splitter.splitFile(inputFile,outputDir,holdingCapacity);
    }
}
