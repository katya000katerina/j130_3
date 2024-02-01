package org.j130.producerconsumer;


import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> storage = new LinkedList<>();
        for (int i = 1; i <= 3; i++) {
            Thread producer = new Thread(new Producer(storage), "Producer №" + i);
            producer.start();

            Thread consumer = new Thread(new Consumer(storage), "Consumer №" + i);
            consumer.start();
        }
    }
}