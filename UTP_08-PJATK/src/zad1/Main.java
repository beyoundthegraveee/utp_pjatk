/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad1;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
  public static void main(String[] args) {
    Map<String, List<String>> map;
    try {
      URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream ()));
      map = bufferedReader.lines()
              .collect(Collectors.groupingBy(
                      str -> {
                        char[] array = str.toCharArray();
                        Arrays.sort(array);
                        return new String(array);
                      },
                      Collectors.toList()
              ));

      int count = map.values().stream()
                      .mapToInt(List::size)
                              .max()
                                      .orElse(0);


        map.entrySet().stream()
                .filter(entry -> entry.getValue().size() == count)
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry-> {
                    entry.getValue().forEach(anagram -> System.out.print(anagram + " "));
                    System.out.println();
                });

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

