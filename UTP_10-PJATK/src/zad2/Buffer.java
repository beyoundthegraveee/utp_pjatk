package zad2;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    List<Towar> buffer = new ArrayList<>();



    public void addToBuffer(Towar towar){
        buffer.add(towar);

    }

    public void removeFromBuffer(){
        buffer.remove(0);
    }

    public List<Towar> getBuffer() {
        return buffer;
    }

}