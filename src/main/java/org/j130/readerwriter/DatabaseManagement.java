package org.j130.readerwriter;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.j130.Utilities.getRandomNumberOfMillis;

public class DatabaseManagement {
    private final Queue<Integer> database;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private final AtomicInteger readersNumber = new AtomicInteger();

    public DatabaseManagement(Queue<Integer> database) {
        this.database = database;
    }

    public void read() throws InterruptedException {
        System.out.printf("%s wants to read the database.%n", Thread.currentThread().getName());
        readLock.lock();
        readersNumber.incrementAndGet();
        System.out.printf("%s is reading the database.%n", Thread.currentThread().getName());
        Thread.sleep(getRandomNumberOfMillis()); //time to read
        database.peek();
        System.out.printf("%s is disconnecting. Current number of readers: %d. Current number of writers: 0%n", Thread.currentThread().getName(), readersNumber.decrementAndGet());
        readLock.unlock();
    }

    public void write() throws InterruptedException {
        System.out.printf("%s wants to write to the database.%n", Thread.currentThread().getName());
        writeLock.lock();
        System.out.printf("%s is writing to the database. Current number of readers: 0. Current number of writers: 1%n", Thread.currentThread().getName());
        Thread.sleep(getRandomNumberOfMillis()); //time to write
        database.add(new Random().nextInt());
        System.out.printf("%s is disconnecting. Current number of readers: 0. Current number of writers: 0.%n", Thread.currentThread().getName());
        writeLock.unlock();
    }
}
