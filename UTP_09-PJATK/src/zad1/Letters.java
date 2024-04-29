package zad1;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Letters{

     String letters;

     List<Thread> threads;


    public Letters(String letters) {
        this.letters = letters;
        this.threads = new ArrayList<>();
        for (char letter : letters.toCharArray()){
            Thread thread = new Thread(()->{
                while (!Thread.interrupted()) {
                    System.out.print(letter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Thread "+letter);
            threads.add(thread);
        }


    }
    public List<Thread> getThreads() {
        return threads;
    }

}
