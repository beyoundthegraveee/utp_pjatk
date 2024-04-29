package zad1;

import java.util.function.Function;

public class InputConverter<T> {

    private T data;

    public InputConverter(T data) {
        this.data = data;

    }

    public <R> R convertBy(Function... func){
        R result = null;

        for(int i = 0; i < func.length; i++){
            result = (R) func[i].apply((i == 0) ? data : result);
        }

        return result;
    }

}
