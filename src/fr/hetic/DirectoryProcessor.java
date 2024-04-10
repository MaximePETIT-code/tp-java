package fr.hetic;

import java.io.IOException;
import java.nio.file.*;

public class DirectoryProcessor {
    private FileProcessor fileProcessor;

    public DirectoryProcessor(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    public void process(String dirPath) {
        String SUFFIX_FILE_EXTENSION = ".op";
        if (!Files.isDirectory(Paths.get(dirPath))) { System.out.println("Invalid directory path: " + dirPath); return; }
        try { Files.walk(Paths.get(dirPath)).filter(path -> path.toString().endsWith(SUFFIX_FILE_EXTENSION)).forEach(path -> fileProcessor.process(path.toString())); } 
        catch (IOException e) { e.printStackTrace(); }
    }
}