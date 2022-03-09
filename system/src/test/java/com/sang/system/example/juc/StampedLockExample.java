package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.StampedLock;

/**
 * @author hxy
 * @date 2022/3/9 17:30
 **/
class StampedLockExample {

    @Test
    void stampedLockExample() {

        // 并且该锁不是直接实现Lock或ReadWriteLock接口，而是其在内部自己维护了一个双向阻塞队列
        //该锁提供了三种模式的读写控制，当调用获取锁的系列函数时，会返回一个long型的变量，我们称之为戳记（stamp），这个戳记代表了锁的状态
        // 不可重入写锁，不可重入读锁，乐观读锁 三种锁可以互相转换
        StampedLock stampedLock = new StampedLock();
        // 类似于ReentrantReadWriteLock的写锁（不同的是这里的写锁是不可重入锁）
        // 请求该锁成功后会返回一个stamp变量用来表示该锁的版本
        long l = stampedLock.writeLock();
        long l1 = stampedLock.tryWriteLock();
        stampedLock.unlockWrite(l);

        // 悲观读锁
        // 类似于ReentrantReadWriteLock的读锁（不同的是这里的读锁是不可重入锁) 共享锁 悲观读锁
        // 写锁未被获取的情况下可以由多个线程获取读锁
        long l2 = stampedLock.readLock();
        stampedLock.unlock(l2);

        // 乐观读锁，不用解锁
        // 没有通过CAS设置锁的状态，仅仅通过位运算测试
        // 简单地返回一个非0的stamp版本信息。获取该stamp后在具体操作数据前还需要调用validate方法验证该stamp是否已经不可用
        long l3 = stampedLock.tryOptimisticRead();
        if (stampedLock.validate(l3)) {

        }


        // 锁的转换
        // 下面几种情况下会成功转换 ● 当前锁已经是写锁模式了。● 当前锁处于读锁模式，并且没有其他线程是读锁模式● 当前处于乐观读模式，并且当前写锁可用。
        long l4 = stampedLock.tryConvertToWriteLock(l2);

        long l5 = stampedLock.tryConvertToReadLock(l2);



    }
}
