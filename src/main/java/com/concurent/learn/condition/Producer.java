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
public class Producer implements Runnable{

    private Queue<String> msg;
    private int maxSize;
    private Condition condition;
    private Lock lock;

    public Producer(Queue<String> msg, int maxSize, Condition condition, Lock lock) {
        this.msg = msg;
        this.maxSize = maxSize;
        this.condition = condition;
        this.lock = lock;
    }


    @Override
    public void run() {
        int i=0;
        while (true){
            i++;
            lock.lock();
            while (msg.size()==maxSize){
                System.out.println("生产者队列满了，先等待");
                try {
                    condition.await();//阻塞线程 并释放锁
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("生产消息：" +i);
            msg.add("生产者的消息内容" + i);
            //唤醒 消费者的线程
            condition.signal();
            lock.unlock();
        }
    }
}
