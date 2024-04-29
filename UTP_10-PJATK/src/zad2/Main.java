/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

  public static void main(String[] args) {
    File file = new File("../Towary.txt");
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    ThreadA threadA = new ThreadA(file, lock, condition);
    threadA.start();
    ThreadB threadB = new ThreadB(threadA.getBuffer(), lock, condition);
    threadB.start();


  }
}


