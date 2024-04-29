package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListCreator<T> extends ArrayList<T> {
    List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }
    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Predicate<T> predicate) {
        List<T> list1 = new ArrayList<>();
        for (T element : list){
            if (predicate.test(element)){
                list1.add(element);
            }
        }
        return new ListCreator<>(list1);
    }

    public <R> List<R> mapEvery(Function<T, R> mapper) {
        List<R> list2 = new ArrayList<>();
        for (T element : list) {
            list2.add(mapper.apply(element));
        }
        return list2;
    }
}