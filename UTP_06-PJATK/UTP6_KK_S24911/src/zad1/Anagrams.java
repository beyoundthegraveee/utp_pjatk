/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad1;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Anagrams {

    String path;

    List<List<String>> result = new ArrayList<>();


    public Anagrams(String allWords) {
        this.path = allWords;
    }

    public List<List<String>> getSortedByAnQty() {
        Map<String, List<String>> anagramMap = new HashMap<>();
        try(BufferedReader bufferedReader = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = bufferedReader.readLine())!=null){
                String[] tab = line.split(" ");
                for (String str : tab){
                    char[] charArray = str.toCharArray();
                    Arrays.sort(charArray);
                    String sortedWord = new String(charArray);
                    if (anagramMap.containsKey(sortedWord)) {
                        anagramMap.get(sortedWord).add(str);
                    } else {
                        List<String> anagramGroup = new ArrayList<>();
                        anagramGroup.add(str);
                        anagramMap.put(sortedWord, anagramGroup);
                        result.add(anagramGroup);
                    }
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
        result.sort((a1,b1)-> Integer.compare(b1.size(), a1.size()));
        return result;
    }

    public String getAnagramsFor(String next) {
        String line = "";
        for (List<String> list : result){
            if(list.contains(next)){
                list.remove(next);
                line = next+": "+list;
            }
        }

        return line;
    }
}
