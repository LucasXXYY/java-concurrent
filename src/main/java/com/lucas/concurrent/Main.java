package com.lucas.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static class ThreadUnsafe {
        private int value;

        public int getNextValue() {
            return value++;
        }
    }

    static class ThreadSafe {
        private int value;

        public synchronized int getNextValue() {
            return value++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ThreadUnsafe threadUnsafe = new ThreadUnsafe();
        ThreadSafe threadSafe = new ThreadSafe();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                threadUnsafe.getNextValue();
                threadSafe.getNextValue();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(threadUnsafe.getNextValue());
        System.out.println(threadSafe.getNextValue());
        executorService.shutdown();
    }
}
