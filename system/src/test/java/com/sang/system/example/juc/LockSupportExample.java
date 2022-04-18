package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author hxy
 * @date 2022/2/14 16:02
 **/
class LockSupportExample {

//    @Test
    void lockSupportExample() {
        // 如果thread之前没有调用park，则调用unpark方法后，再调用park方法，其会立刻返回
        System.out.println("begin park");
        // unpark方法使线程持有许可证
        LockSupport.unpark(Thread.currentThread());
        // park方法中一旦持有许可证线程便会继续执行,没有许可证则会阻塞挂起等待获得许可证
        LockSupport.park();
        System.out.println("end park");
    }

    /**
     * park方法返回时不会告诉你因何种原因返回，所以调用者需要根据之前调用park方法的原因，
     * 再次检查条件是否满足，如果不满足则还需要再次调用park方法。
     * 例如，根据调用前后中断状态的对比就可以判断是不是因为被中断才返回的。
     * @throws InterruptedException
     */
//    @Test
    void lockSupportExample2() throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("begin park");

            // 1
//            LockSupport.park();
//            or //经过指定时间后自动返回
//            LockSupport.parkNanos();
//            or // 带有blocker参数的park方法会在jstack时提供线程被阻塞位置的代码信息
//            LockSupport.park(this);
//            or // blocker 和到指定时间点后返回
//            LockSupport.parkUntil(this,9999990000L);

            // 2 线程被中断后也会从阻塞状态返回
            while(!Thread.currentThread().isInterrupted()) {
                System.out.println("park again");
                LockSupport.park();
            }

            System.out.println("end park");
        });

        thread.start();

        Thread.sleep(2000);

        System.out.println("begin unpack");

        // 1
//        LockSupport.unpark(thread);

        // 2
        thread.interrupt();
    }


}
