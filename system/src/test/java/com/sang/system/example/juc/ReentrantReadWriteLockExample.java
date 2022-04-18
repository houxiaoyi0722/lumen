package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author hxy
 * @date 2022/3/9 13:51
 **/
class ReentrantReadWriteLockExample {


//    @Test
    void reentrantReadWriteLockExample() {

        /*
         * 读锁是可以多线程共享的，即共享锁，而写锁是排他锁，
         * 在更改时候不允许其他线程操作。读写锁底层是同一把锁（基于同一个AQS），所以会有同一时刻不允许读写锁共存的限制。
         */
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        /*
         * AQS中只维护了一个state状态，而ReentrantReadWriteLock则需要维护读状态和写状态,
         * ReentrantReadWriteLock巧妙地使用state的高16位表示读状态，也就是获取到读锁的次数
         * 使用低16位表示获取到写锁的线程的可重入次数。
         */

        /*
         * 写锁是一个可重入独占锁，获取了写锁的线程可以直接获取读锁，但是在释放是需要两个锁都释放
         */
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        /*
         * 加锁时判断其他线程是否已经持有读锁\写锁，读锁\写锁被持有后当前线程进入aqs挂起
         * 如果读锁\写锁未被获取，则锁获取成功否则挂起阻塞
         */
        writeLock.lock();

        /*
         * 释放锁且从队列激活
         */
        writeLock.unlock();


        Condition condition = writeLock.newCondition();

        /*
         * 读锁是一个共享锁，获取读锁时如果写锁已经被持有则当前线程进入aqs阻塞队列等待写锁释放
         */
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

        readLock.lock();
        /*
         * 读锁释放时使用cas操作将当前的读次数-1，如果-1后为0则所有读锁都已经释放，从队列激活一个获取写锁被阻塞的线程
         * 如果-1后不为0说明还有其他线程获取读锁，返回false
         */
        readLock.unlock();

        // 抛出UnsupportedOperationException
//        Condition condition1 = readLock.newCondition();

        /*
         * 锁降级 ： 是指保持住当前的写锁（已拥有），再获取读锁，随后释放写锁的过程。
         * 如果只使用写锁，那么释放写锁之后，其他线程就会获取到写锁或读锁，使用锁降级可以在释放写锁前获取读锁，这样其他的线程就只能获取读锁，对这个数据进行读取，但是不能获取写锁进行修改，只有当前线程释放了读锁之后才可以进行修改。
            相对于一直使用写锁，锁降级可以减少其他读线程的阻塞。
         */
    }

}
