package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author hxy
 * @date 2022/2/10 13:50
 **/
class TestThreadBaseExample {

    class Thread1 implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread1");
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread2");
        }
    }

    class Thread3 implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Thread3";
        }
    }

    @Test
    void threadBase() {
        new Thread(new Thread1()).start();

        new Thread2().start();

        FutureTask<String> thread3 = new FutureTask<>(new Thread3());
        new Thread(thread3).start();
        try {
            String s = thread3.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
