package zad2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgLang {

    private List<String> source = new ArrayList<>();



    public ProgLang(String nazwaPliku) {
        try(BufferedReader bf = Files.newBufferedReader(Path.of(nazwaPliku))){
            String line = "";
            while ((line = bf.readLine())!=null){
                String[] parts = line.split("\\t");
                Set<String> uniqueElements = new HashSet<>();
                String out = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    if (uniqueElements.add(parts[i])){
                        out+="\t"+parts[i];
                    }
                }
                source.add(out);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getLangsMap() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (String str : source){
            List<String> list = new ArrayList<>();
            String [] tab = str.split("\\t");
            for (int i = 1; i < tab.length; i++) {
                list.add(tab[i]);
            }
            result.put(tab[0], list);
        }

        return result;
    }

    public Map<String, List<String>> getProgsMap() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (String str : source){
            String [] tab = str.split("\\t");
            String lang = tab[0];
            for (int j = 1; j < tab.length; j++) {
                String letter = tab[j];
                if(result.containsKey(tab[j])){
                    result.get(letter).add(lang);
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(lang);
                    result.put(letter, list);
                }
            }
        }

        return result;
    }


    public Map<String, List<String>> getLangsMapSortedByNumOfProgs() {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (String str : source){
            List<String> list = new ArrayList<>();
            String [] tab = str.split("\\t");
            for (int i = 1; i < tab.length; i++) {
                list.add(tab[i]);
            }

            result.put(tab[0], list);
        }



        return sorted(result, (a1,a2)-> Integer.compare(a2.getValue().size(), a1.getValue().size()));
    }

    public Map<String, List<String>> getProgsMapSortedByNumOfLangs() {
        Map<String, List<String>> unsorted = new LinkedHashMap<>();
        for (String str : source){
            String [] tab = str.split("\\t");
            String lang = tab[0];
            for (int j = 1; j < tab.length; j++) {
                String letter = tab[j];
                if(unsorted.containsKey(tab[j])){
                    unsorted.get(letter).add(lang);
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(lang);
                    unsorted.put(letter, list);
                }
            }

        }


        Map<String, List<String>> FirstSorted = unsorted.entrySet().stream().sorted((a1, a2)->
                a1.getKey().compareTo(a2.getKey())).collect(Collectors.toMap(e->e.getKey(),
                e->e.getValue()));

        return sorted(FirstSorted, (a1, a2)->Integer.compare(a2.getValue().size(), a1.getValue().size()));

    }


    public <T,R> Map<T, R> sorted(Map<T,R> map,Comparator<Map.Entry<T,R>> comparator){
        Map<T,R> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<T,R>> list = new ArrayList<>(map.entrySet());

        list.sort(comparator);

        for (Map.Entry<T,R> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }

    public <T,R> Map<T,R> filtered(Map<T,R> map, Predicate<T> predicate){
        Map<T,R> filteredmap = new LinkedHashMap<>();

        for (Map.Entry<T,R> entry : map.entrySet()){
            if(predicate.test(entry.getKey())){
                filteredmap.put(entry.getKey(),entry.getValue());
            }
        }
        return filteredmap;

    }


    public Map<String, List<String>> getProgsMapForNumOfLangsGreaterThan(int i) {
        Map<String, List<String>> unsorted = new LinkedHashMap<>();
        for (String str : source){
            String [] tab = str.split("\\t");
            String lang = tab[0];
            for (int j = 1; j < tab.length; j++) {
                String letter = tab[j];
                if(unsorted.containsKey(tab[j])){
                    unsorted.get(letter).add(lang);
                }else {
                    List<String> list = new ArrayList<>();
                    list.add(lang);
                    unsorted.put(letter, list);
                }
            }

        }


        return filtered(unsorted, a-> unsorted.get(a).size()>i);
    }



}
