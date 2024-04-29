package zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadA implements Runnable {

    File file;

    Buffer buffer = new Buffer();

    Thread thread;

    public ThreadA(File file) {
        this.file = file;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String str;
                int count = 0;
                    while ((str = bufferedReader.readLine()) != null) {
                        synchronized (buffer) {
                            String[] tab = str.split(" ");
                            buffer.addToBuffer(new Towar(Integer.parseInt(tab[0]), Double.parseDouble(tab[1])));
                            count++;
                            if (count % 200 == 0) {
                               System.out.println("utworzono " + count + " obiektów");
                            }
                            buffer.notify();
                            buffer.wait();

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
