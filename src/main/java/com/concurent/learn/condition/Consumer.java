package com.concurent.learn.condition;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/01
 */
public class Consumer implements Runnable{

    private Queue<String> msg;
    private Lock lock;
    private Condition condition;

    public Consumer(Queue<String> msg, Lock lock, Condition condition) {
        this.msg = msg;
        this.lock = lock;
        this.condition = condition;
    }


    @Override
    public void run() {
        int i =0;
        while (true){
            i++;
            lock.lock();
            while (msg.isEmpty()){
                System.out.println("消费者队列空了，先等待");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费消息：" + msg.remove());
            condition.signal();
            lock.unlock();
        }
    }
}
