package zad1;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Futil {


    public static void processDir(String dirName, String resultFileName) {

         Path startDir = Path.of(dirName);


        try {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(resultFileName),StandardCharsets.UTF_8 );
            Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {

                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }


                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("visitFile: " + file);
                    List<String> list = new ArrayList<>();
                    String path = file.toString();
                    BufferedReader bufferedReader = Files.newBufferedReader(Path.of(path), Charset.forName("Cp1250"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        byte[] bytes = line.getBytes("Cp1250");
                        String str = new String(bytes, StandardCharsets.UTF_8);
                        list.add(str);
                    }

                    for (String str : list){
                           bufferedWriter.write(str + "\n");
                    }

                    bufferedReader.close();

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.out.println("Failed to access file: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    boolean finishedSearch = Files.isSameFile(dir, startDir);
                    if (finishedSearch) {
                        System.out.println("Done!");
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }


            });

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
