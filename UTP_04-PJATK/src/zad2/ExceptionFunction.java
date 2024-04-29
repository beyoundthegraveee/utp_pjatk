package zad2;
import java.util.function.Function;


public interface ExceptionFunction<T,R> extends Function<T,R> {
    @Override
    default R apply(T t){
        try{
            return applyThrows(t);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    R applyThrows(T t) throws Exception;
}