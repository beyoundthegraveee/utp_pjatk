package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadB implements Runnable {


    Buffer buffer;

    Thread thread;


    double TotalWaga;

    public ThreadB(Buffer buffer) {
        this.buffer = buffer;
        this.thread = new Thread(this);
    }


    @Override
    public void run() {
        int count = 0;
            while (count <= 10000) {
                synchronized (buffer) {
                    if (buffer.isEmptyBuffer()) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Iterator<Towar> iterator = buffer.getBuffer().iterator();
                    while (iterator.hasNext()) {
                        Towar towar = iterator.next();
                        TotalWaga += towar.getWaga();
                        count++;
                        if (count % 100 == 0) {
                            System.out.println("policzono wage " + count + " towarów");
                        }
                        buffer.notify();
                    }
                    buffer.removeFromBuffer();
                }
            }
            System.out.println("Sumaryczna waga wszystkich towarów: " + TotalWaga);

    }


    public void start() {
        thread.start();
    }

}
