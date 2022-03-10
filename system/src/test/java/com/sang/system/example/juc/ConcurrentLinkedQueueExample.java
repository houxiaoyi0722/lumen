package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author hxy
 * @date 2022/3/10 11:56
 **/
class ConcurrentLinkedQueueExample {

    @Test
    void concurrentLinkedQueueExample() {
        /*
         * 线程安全的无界(无容量限制)非阻塞(cas操作)队列(单向队列头出尾进)，其底层数据结构使用单向链表实现，对于入队和出队操作使用CAS来实现线程安全
         * 其中有两个volatile类型的Node节点分别用来存放队列的首、尾节点。
         * 默认头、尾节点都是指向item为null的哨兵节点
         */
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

        boolean add = concurrentLinkedQueue.add("1");
    }


}
