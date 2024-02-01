package org.j130.producerconsumer;

import java.util.Queue;

import static org.j130.Utilities.getRandomNumberOfProducts;
import static org.j130.Utilities.getRandomNumberOfMillis;

public class Consumer implements Runnable {
    private final Queue<Integer> storage;

    public Consumer(Queue<Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            try {
                getProducts(getRandomNumberOfProducts());
                Thread.sleep(getRandomNumberOfMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getProducts(int productsToGet) throws InterruptedException {
        synchronized (storage) {
            String threadName = Thread.currentThread().getName();
            System.out.printf("%s arrived at the storage.%n", threadName);
            while (storage.size() < productsToGet) {
                System.out.printf("%s is waiting to get %d %s.%n", threadName, productsToGet, productsToGet > 1? "products" : "product");
                storage.wait();
            }
            Thread.sleep(getRandomNumberOfMillis()); //time to get products
            for (int i = 0; i < productsToGet; i++) {
                storage.remove();
            }
            System.out.printf("%s got %d %s and left the storage.%nNumber of products available in the storage: %d%n",
                    threadName, productsToGet, productsToGet > 1? "products" : "product", storage.size());
        }
    }
}
