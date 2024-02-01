package org.j130.readerwriter;

import java.util.LinkedList;
import java.util.Queue;

import static org.j130.Utilities.getRandomNumberOfMillis;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> database = new LinkedList<>();
        DatabaseManagement dm = new DatabaseManagement(database);

        for (int i = 1; i <= 10; i++) {
            Thread reader = new Thread(() -> {
                try {
                    while (true) {
                        dm.read();
                        Thread.sleep(getRandomNumberOfMillis());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "Reader №" + i);
            reader.start();
        }

        for (int i = 1; i <= 3; i++) {
            Thread writer = new Thread(() -> {
                try {
                    while (true) {
                        dm.write();
                        Thread.sleep(getRandomNumberOfMillis());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "Writer №" + i);
            writer.start();
        }
    }
}
