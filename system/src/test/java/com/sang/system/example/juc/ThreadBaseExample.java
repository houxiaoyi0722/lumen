package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.LongAdder;

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

    @Test
    void threadMethod() throws InterruptedException {
        LinkedList<String> list = new LinkedList<>();

        Thread producer1 = new Thread(() -> {
            try {
                synchronized (list) {
                    for (;!Thread.currentThread().isInterrupted();) {

                        Thread.sleep(100);
                        // 有产品,等待消费
                        if (list.size() > 2) {
                            System.out.println("producer1等待 " + list.size());
                            list.wait();
                            list.notify();

                        } else {
                            list.push("producer1");
                            System.out.println("producer1生产 " + list.size());
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread producer2 = new Thread(() -> {
            synchronized (list) {
                try {
                    for (;!Thread.currentThread().isInterrupted();) {
                        Thread.sleep(100);
                        // 有产品,等待消费
                        if (list.size() > 2) {

                                System.out.println("producer2等待 " + list.size());
                                // wait方法必须
                                // wait如果持有多个锁只能释放当前引用的锁
                                list.wait();
                                // 随机唤醒在该引用上等待的一个线程
                                // notifyAll唤醒全部,唤醒后需要重新竞争锁
                                list.notify();

                        } else {
                            list.push("producer2");
                            System.out.println("producer2生产 " + list.size());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        LongAdder longAdder = new LongAdder();
        Thread consumer = new Thread(() -> {
            synchronized (list) {
                for (;;) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (longAdder.sum() > 20) {
                        producer1.interrupt();
                        producer2.interrupt();
                        break;
                    }

                    if (list.size() <= 0) {
                        list.notify();
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        longAdder.add(1);
                        System.out.println("consumer : " + list.pop() + "剩余 " + list.size());
                    }
                }
            }
        });
        producer1.start();
        producer2.start();
        consumer.start();

        producer1.join();
        producer2.join();
        consumer.join();
        System.out.println("over");
    }


}
