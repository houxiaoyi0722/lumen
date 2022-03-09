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

    class Point {
        // 成员变量
        private double x, y;
        // 锁实例
        private final StampedLock sl = new StampedLock();

        // 排它锁——写锁（writeLock）
        void move(double deltaX, double deltaY) {
            long stamp = sl.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                sl.unlockWrite(stamp);
            }
        }

        // 乐观读锁（tryOptimisticRead）
        double distanceFromOrigin() {
            //（1）尝试获取乐观读锁
            long stamp = sl.tryOptimisticRead();
            //（2）将全部变量复制到方法体栈内
            double currentX = x, currentY = y;
            //（3）检查在（1）处获取了读锁戳记后，锁有没被其他写线程排它性抢占
            if (!sl.validate(stamp)) {
                //（4）如果被抢占则获取一个共享读锁（悲观获取）
                stamp = sl.readLock();
                try {
                    //（5）将全部变量复制到方法体栈内
                    currentX = x;
                    currentY = y;
                } finally {
                    //（6）释放共享读锁
                    sl.unlockRead(stamp);
                }
            }
            //（7）返回计算结果
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        // 使用悲观锁获取读锁，并尝试转换为写锁
        void moveIfAtOrigin(double newX, double newY) {
            //（1）这里可以使用乐观读锁替换
            long stamp = sl.readLock();
            try {
                //（2）如果当前点在原点则移动
                while (x == 0.0 && y == 0.0) {
                    //（3）尝试将获取的读锁升级为写锁
                    long ws = sl.tryConvertToWriteLock(stamp);
                    //（4）升级成功，则更新戳记，并设置坐标值，然后退出循环
                    if (ws != 0L){
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else{
                        //（5）读锁升级写锁失败则释放读锁，显式获取独占写锁，然后循环重试
                        sl.unlockRead(stamp);
                        stamp = sl.writeLock();
                    }
                }
            } finally {
                //（6）释放锁
                sl.unlock(stamp);
            }
        }
    }
}
