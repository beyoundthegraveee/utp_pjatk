/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad1;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*<--
 *  niezbędne importy
 */
public class Main {
  public static void main(String[] args) {

    Function<String, List<String>> flines = s -> {
      File file = new File(s);
      List<String> list = new ArrayList<>();
      try {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
          list.add(scanner.nextLine());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return list;
    };


    Function<List<String>, String> join = s -> {
      String text = "";
      for (String str : s){
        text+=str;
      }
      return text;
    };


     Function<String, List<Integer>> collectInts = s -> {
      Pattern integerPattern = Pattern.compile("-?\\d+");
      List<Integer> list = new ArrayList<>();
      Matcher matcher = integerPattern.matcher(s);
      while (matcher.find()){
        list.add(Integer.valueOf(matcher.group()));
      }
      return list;

    };

    Function<List<Integer>, Integer> sum = s ->{
      int count = 0;
      for (Integer element : s){
        count+=element;
      }
      return count;
    };

    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines,join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }

}
