/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad1;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

  public static void main(String[] args) {
    File file = new File("../Towary.txt");
    ThreadA threadA = new ThreadA(file);
    threadA.start();
    ThreadB threadB = new ThreadB(threadA.getBuffer());
    threadB.start();








  }
}
