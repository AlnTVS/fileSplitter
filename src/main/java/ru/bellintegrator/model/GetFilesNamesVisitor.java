package ru.bellintegrator.model;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class GetFilesNamesVisitor extends SimpleFileVisitor<Path> {
    Set<String> stringSet;

    public GetFilesNamesVisitor(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
        if (!Files.isDirectory(path)) {
            System.out.println("File catch: " + path.getFileName());
            stringSet.add(path.normalize().toString());
        }
        return FileVisitResult.CONTINUE;
    }
}