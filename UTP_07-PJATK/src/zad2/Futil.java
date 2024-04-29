package zad2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {

        Path startDir = Path.of(dirName);

        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of(resultFileName), StandardCharsets.UTF_8 )){
            Files.walk(startDir, Integer.MAX_VALUE)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .forEach(file -> processFile(file, bufferedWriter));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

}

    private static void processFile(Path file, BufferedWriter bufferedWriter) {
        System.out.println("visitFile: " + file);
        try {
            Files.lines(file, Charset.forName("Cp1250"))
                    .map(line -> {
                        String str;
                        try {
                            byte[] bytes = line.getBytes("Cp1250");
                            str = new String(bytes, StandardCharsets.UTF_8);
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        return str;
                    })
                    .forEach(str ->
                    {
                        try {
                            bufferedWriter.write(str + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
