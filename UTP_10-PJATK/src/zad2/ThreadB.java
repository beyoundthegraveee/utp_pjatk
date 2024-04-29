package zad2;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadB implements Runnable {


    Buffer buffer;

    ReentrantLock lock;


    Condition condition;



    Thread thread;

    double TotalWaga;

    public ThreadB(Buffer buffer, ReentrantLock lock, Condition condition) {
        this.buffer = buffer;
        this.lock = lock;
        this.condition = condition;
        this.thread = new Thread(this);
    }


    @Override
    public void run() {
        int count = 0;
            while (count <= 10000) {
                lock.lock();
                try {
                    while (buffer.getBuffer().isEmpty()) {
                        condition.await();
                    }
                    List<Towar> copyBuffer = new ArrayList<>(buffer.getBuffer());
                    for (Towar towar : copyBuffer) {
                        TotalWaga += towar.getWaga();
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("policzono wage " + count + " towarów");
                        }
                    }
                    buffer.removeFromBuffer();
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        System.out.println("Sumaryczna waga wszystkich towarów: " + TotalWaga);

    }


    public void start() {
        thread.start();
    }



}