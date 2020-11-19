package com.concurent.learn.condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/01
 */
public class ConditionApp {

    public static void main(String[] args) {
        Queue queue = new LinkedList();
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int maxSize = 5;
        Producer producer = new Producer(queue,maxSize,condition,lock);
        Consumer consumer = new Consumer(queue, lock, condition);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        t1.start();
        t2.start();
    }
}
