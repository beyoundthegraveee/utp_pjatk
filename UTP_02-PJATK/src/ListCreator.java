import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> extends ArrayList<T> {
    List<T> list;

    private ListCreator(List<T> list) {
        this.list = list;
    }
    public static <T> ListCreator<T> collectFrom(List<T> list) {
        return new ListCreator<>(list);
    }

    public ListCreator<T> when(Selector<T> selector) {
        List<T> list1 = new ArrayList<>();
        for (T element : list) {
            if (selector.select(element)) {
                list1.add(element);
            }
        }
        return new ListCreator<>(list1);
    }

    public <R> List<R> mapEvery(Mapper<T, R> mapper) {
        List<R> list2 = new ArrayList<>();
        for (T element : list) {
            list2.add(mapper.map(element));
        }
        return list2;
    }
}