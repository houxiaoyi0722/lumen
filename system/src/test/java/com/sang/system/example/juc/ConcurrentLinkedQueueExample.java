package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue的底层使用单向链表数据结构来保存队列元素，
 * 每个元素被包装成一个Node节点。队列是靠头、尾节点来维护的，创建队列时头、
 * 尾节点指向一个item为null的哨兵节点。第一次执行peek或者first操作时会把head指向第一个真正的队列元素。
 * 由于使用非阻塞CAS算法，没有加锁，所以在计算size时有可能进行了offer、poll或者remove操作，导致计算的元素个数不精确，
 * 所以在并发情况下size函数不是很有用。
 * @author hxy
 * @date 2022/3/10 11:56
 **/
class ConcurrentLinkedQueueExample {

//    @Test
    void concurrentLinkedQueueExample() {
        /*
             线程安全的无界(无容量限制)非阻塞(cas操作)队列(单向队列头出尾进)，其底层数据结构使用单向链表实现，对于入队和出队操作使用CAS来实现线程安全
             其中有两个volatile类型的Node节点分别用来存放队列的首、尾节点。
             默认头、尾节点都是指向item为null的哨兵节点
         */
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

        // 插入的数据永远不能为null
        boolean add = concurrentLinkedQueue.add("1");

        /*
            进行CAS竞争失败的线程会通过循环一次次尝试进行CAS操作，直到CAS成功才会返回，
            也就是通过使用无限循环不断进行CAS尝试方式来替代阻塞算法挂起调用线程。
            相比阻塞算法，这是使用CPU资源换取阻塞所带来的开销。
         */
        boolean offer = concurrentLinkedQueue.offer("2");

        /*
            队列为空返回null
            poll方法在移除一个元素时，只是简单地使用CAS操作把当前节点的item值设置为null，
            然后通过重新设置头节点将该元素从队列里面移除，被移除的节点就成了孤立节点，
            这个节点会在垃圾回收时被回收掉。另外，如果在执行分支中发现头节点被修改了，要跳到外层循环重新获取新的头节点。
         */
        String poll = concurrentLinkedQueue.poll();

        /*
            获取队列头部一个元素（只获取不移除），如果队列为空则返回null
            peek操作的代码与poll操作类似，只是前者只获取队列头元素但是并不从队列里将它删除，
            而后者获取后需要从队列里面将它删除。另外，在第一次调用peek操作时，会删除哨兵节点，
            并让队列的head节点指向队列里面第一个元素或者null。
         */
        String peek = concurrentLinkedQueue.peek();

        /*
            并发环境下可能不准确
         */
        int size = concurrentLinkedQueue.size();

        /*
            队列里面存在该元素则删除该元素，如果存在多个则删除第一个，并返回true，否则返回false
         */
        boolean remove = concurrentLinkedQueue.remove("2");
    }


}
