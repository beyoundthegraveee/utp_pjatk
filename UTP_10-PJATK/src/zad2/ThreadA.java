package zad2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadA implements Runnable {

    File file;



    ReentrantLock lock;



    Condition condition;

    Buffer buffer = new Buffer();

    Thread thread;

    public ThreadA(File file, ReentrantLock lock, Condition condition) {
        this.file = file;
        this.lock = lock;
        this.condition = condition;
        this.thread = new Thread(this);
    }
    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            int count = 0;
            while ((str = bufferedReader.readLine()) != null) {
                lock.lock();
                try {
                    String[] tab = str.split(" ");
                    buffer.addToBuffer(new Towar(Integer.parseInt(tab[0]), Double.parseDouble(tab[1])));
                    count++;
                   if (count % 200 == 0) {
                        System.out.println("utworzono " + count + " obiektów");
                    }
                   condition.signal();
                   condition.await();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
            bufferedReader.close();
        } catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void start() {
        thread.start();
    }

}