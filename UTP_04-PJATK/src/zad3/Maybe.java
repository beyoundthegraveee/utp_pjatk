package zad3;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {

    private T element;

    private static Maybe<?> maybe = new Maybe<>(null);

    public Maybe(T element) {
        this.element = element;
    }

    public static <T> Maybe<T> of(T element){
        return element!=null ? new Maybe<>(element) : (Maybe<T>) maybe;
    }

    public void ifPresent(Consumer<T> cons){
        if(element!=null){
            cons.accept(element);
        }
    }

    public <S> Maybe<S> map(Function<T,S> func){
        if (element!=null){
            S result = func.apply(element);
            return new Maybe(result);
        }else {
            return (Maybe<S>) maybe;
        }
    }


    public T get() {
        if(element == null){
            throw new NoSuchElementException("maybe is empty");
        }
        else {
            return element;
        }
    }


    public T orElse(T defVal) {
        if(element != null){
            return element;
        }else {
            return defVal;
        }
    }

    public boolean isPresent(){
        if(element!=null){
            return true;
        }else {
            return false;
        }
    }

    public Maybe<T> filter(Predicate <T> pred){
        if(pred.test(element)){
            return this;
        }else {
            return (Maybe<T>) maybe;
        }
    }

    @Override
    public String toString() {
        return (element!=null ? "Maybe has a value " + element : "Maybe is empty");
    }
}
