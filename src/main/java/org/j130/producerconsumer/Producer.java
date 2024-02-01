package org.j130.producerconsumer;

import java.util.Queue;
import java.util.Random;

import static org.j130.Utilities.getRandomNumberOfProducts;
import static org.j130.Utilities.getRandomNumberOfMillis;

public class Producer implements Runnable {
    private final Queue<Integer> storage;

    public Producer(Queue<Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                deliverProducts(getRandomNumberOfProducts());
                Thread.sleep(getRandomNumberOfMillis()); //time to get a new batch of products
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void deliverProducts(int productsToDeliver) throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        synchronized (storage) {
            System.out.printf("%s arrived at the storage.%n", threadName);
            Thread.sleep(getRandomNumberOfMillis()); //time to deliver products
            for (int i = 0; i < productsToDeliver; i++) {
                storage.add(new Random().nextInt());
            }
            System.out.printf("%s delivered %d %s and left the storage.%nNumber of products available in the storage: %d%n",
                    threadName, productsToDeliver, productsToDeliver > 1? "products" : "product", storage.size());
            storage.notify();
        }
    }
}
