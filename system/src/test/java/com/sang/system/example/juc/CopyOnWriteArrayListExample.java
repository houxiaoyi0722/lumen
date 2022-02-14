package com.sang.system.example.juc;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author hxy
 * @date 2022/2/14 14:16
 **/
class CopyOnWriteArrayListExample {

    @Test
    void copyOnWriteArrayListExample() throws InterruptedException {
        // CopyOnWriteArrayList 读写弱一致性
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        Thread thread = new Thread(() -> {
            list.add("4");
            list.remove("2");
        });

        // 迭代器获取当前列表中数组引用
        Iterator<String> iterator = list.iterator();

        thread.start();
        thread.join();

        // 数据变更后获取新的数组的引用
        Iterator<String> iterator2 = list.iterator();

        // 两个不同的数组同时存在
        while (iterator.hasNext()) {
            System.out.println("iterator1: " + iterator.next());
        }

        // 两个不同的数组同时存在
        while (iterator2.hasNext()) {
            System.out.println("iterator2: " + iterator2.next());
        }

    }

}
