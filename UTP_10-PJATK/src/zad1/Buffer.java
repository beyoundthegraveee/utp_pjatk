package zad1;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    List<Towar> buffer = new ArrayList<>();

    public synchronized void addToBuffer(Towar towar){
        buffer.add(towar);
    }

    public synchronized void removeFromBuffer(){
        while (buffer.isEmpty()) {
            try {
                buffer.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        buffer.remove(0);
    }

    public List<Towar> getBuffer() {
        return buffer;
    }

    public boolean isEmptyBuffer(){
        return buffer.isEmpty();
    }

}
