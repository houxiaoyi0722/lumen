package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hxy
 * @date 2022/2/25 17:41
 **/
class ReentrantLockExample {

    @Test
    void reentrantLockExample() {
        ReentrantLock reentrantLock = new ReentrantLock();

        // 获取锁,未竞争到锁的线程会进入aqs队列自旋竞争
        reentrantLock.lock();
        // 获取Condition对象:一个reentrantLock可以有多个Condition对象
        // 每个Condition对象都在内部维护了一个等待队列
        Condition condition = reentrantLock.newCondition();

        try {
            // 调用condition对象的await方法后,当前线程会加入到该condition对象的单向等待队列中
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 从队列释放第一个等待的线程竞争锁
        condition.signal();


    }
}
