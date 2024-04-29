package zad1;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class XList<T> extends ArrayList<T>{


    public XList(T... elements) {
        super(List.of(elements));
    }

    public XList(Collection collection){
        super(collection);
    }

    public static <T>XList<T> of(Collection collection) {
        return new XList<>(collection);
    }

    public static <T>XList<T> of(T... t) {
        return new XList<>(t);
    }


    public static XList<String> charsOf(String line) {
        ArrayList<Character> tab  = new ArrayList<>();
        for (char c : line.toCharArray()){
            tab.add(c);
        }
        return new XList<>(tab);
    }

    public static XList<String> tokensOf(String line) {
        return new XList<>(line.split(" "));
    }

    public static XList<String> tokensOf(String line, String sep) {
        return new XList<>(line.split(sep));
    }


    public XList<T> union(T... elements) {
        XList<T> list = new XList<T>(this);
        for (T element: elements){
            list.add(element);
        }
        return list;
    }

    public XList<T> union(Collection<T> collection) {
        this.addAll(collection);
        return this;
    }

    public XList<T> diff(Collection<T> collection) {
        XList<T> list = new XList(this);

        Iterator<T> it = list.iterator();

        while (it.hasNext()){
            if(collection.contains(it.next())){
                it.remove();
            }
        }

        return list;
    }

    public XList<T> diff(T... collection) {
        XList<T> xList = new XList<T>(this);
        xList.retainAll(Arrays.asList(collection));
        return xList;
    }

    public XList<T>unique(){
        return new XList<T>(this.stream().distinct().collect(Collectors.toList()));
    }

    public XList<XList<T>> combine(){

        XList<XList<T>> result = new XList<>();
        XList<XList<T>> source = new XList<>(this);

        int BoxCount = 1;

        for(int n = 0; n < this.size(); n++){
            BoxCount *= ((List<T>) source.get(n)).size();
        }

        int [] position = new int[source.size()];
        int [] tab2 = new int[source.size()];
        tab2[0] = 1;
        int [] helper = new int[source.size()];

        for(int n = 0; n < BoxCount; n++){
            XList<T> box = new XList<>();
            result.add(box);
        }

        for(int i = 0; i < source.size(); i++){

            List<T> list = source.get(i);

            for(int j = 0; j < BoxCount; j++){
                result.get(j).add(list.get(helper[i]));

                position[i]++;

                if(tab2[i] == position[i]){
                    position[i] = 0;
                    helper[i]++;
                }

                if(helper[i] == list.size()){
                    helper[i] = 0;

                    if(i+1 < tab2.length){
                        if(tab2[i+1] == 0){
                            tab2[i+1] = j+1;
                        }
                    }
                }
            }

        }

        return result;
    }



    public <R> XList<R> collect(Function<T,R> function)
    {
        XList<R>list = new XList<R>();

        for (T element : this){
            list.add(function.apply(element));
        }
        return list;
    }

    public String join(String sep) {
        return this.stream().map(Object::toString).collect(Collectors.joining(sep));

    }

    public String join(){
        return join("");
    }


    public void forEachWithIndex(BiConsumer<T,Integer> consumer) {
        for (int i = 0; i < this.size(); i++) {
            consumer.accept(this.get(i), i);
        }

    }
}
