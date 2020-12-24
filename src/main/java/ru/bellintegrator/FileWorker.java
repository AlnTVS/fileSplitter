package ru.bellintegrator;

import ru.bellintegrator.model.GetFilesNamesVisitor;

import java.io.*;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class FileWorker {
    private static int depth = Integer.MAX_VALUE;

    public static void setDepth(int depth) {
        FileWorker.depth = depth;
    }

    public static String getPathToLogs() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь до папки с логами:");
        return scanner.nextLine();
    }

    public static boolean checkIsExistDirectory(Path path) {
        if (!Files.exists(path)) {
            System.err.println("Путь введен не корректно, указаная директория не существует");
            return false;
        } else if (!Files.isDirectory(path)) {
            System.err.println("Путь введен не корректно, укажите путь к папке");
            return false;
        }
        return true;
    }

    public static boolean checkIsExistFile(Path path) {
        if (!Files.exists(path)) {
            System.err.println("Путь введен не корректно, указанный файл не существует");
            return false;
        } else if (Files.isDirectory(path)) {
            System.err.println("Путь введен не корректно, укажите путь к файлу");
            return false;
        }
        return true;
    }

    public static boolean checkFileExtension(Path path, String str) {
        if (!checkIsExistFile(path)) return false;
        String[] tokens = path.getFileName().toString().split(".");
        if (tokens[tokens.length - 1].equals(str)) return true;
        return false;
    }

    public static Set<String> getAllFilesByPath(Path path) {
        Set<String> set = getAllFilesByPathAndName(path, "");
        return set;
    }

    public static Set<String> getAllFilesByPathAndName(Path path, String fileName) {
        Set<String> set = new LinkedHashSet<>();
        try {
            Files.walkFileTree(path, EnumSet.of(FileVisitOption.FOLLOW_LINKS), depth, new GetFilesNamesVisitor(set));
            //Files.walkFileTree(path,new MyFileVisitor(set));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        filterFilesByName(set, fileName);
        return set;
    }

    public static List<String> readFileToStringList(Path path) {
        List<String> stringList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String oneLine;
            while ((oneLine = br.readLine()) != null) {
                stringList.add(oneLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static boolean writeFileByStringList(List<String> stringList, String sPath) {
        Path path = Paths.get(sPath);
//        if(!checkIsExistDirectory(path.subpath(0,path.getNameCount()-2)));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()))) {
            for (String oneString : stringList) {
                bw.write(oneString);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Set<String> filterFilesByName(Set set, String filter) {
        Iterator<String> iterator = set.iterator();
        String pathToFile;
        while (iterator.hasNext()) {
            pathToFile = iterator.next();
            if (!pathToFile.contains(filter)) {
                iterator.remove();
            }
        }
        return set;
    }

    private static Path toOneFormat(Path path) {
        return path.normalize();
    }
}
