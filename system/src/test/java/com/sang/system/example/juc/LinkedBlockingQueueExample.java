package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author hxy
 * @date 2022/3/10 15:47
 **/
class LinkedBlockingQueueExample {

    @Test
    void linkedBlockingQueueExample() {

        /*
            LinkedBlockingQueue也是使用单向链表实现的，其也有两个Node，
            分别用来存放首、尾节点，并且还有一个初始值为0的原子变量count，
            用来记录队列元素个数。另外还有两个ReentrantLock的实例，分别用来控制元素入队和出队的原子性，
            其中takeLock用来控制同时只有一个线程可以从队列头获取元素，其他线程必须等待，
            putLock控制同时只能有一个线程可以获取锁，在队列尾部添加元素，其他线程必须等待。
            另外，notEmpty和notFull是条件变量，
            它们内部都有一个条件队列用来存放进队和出队时被阻塞的线程，其实这是生产者—消费者模型。
            队列
         */
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();




    }
}
