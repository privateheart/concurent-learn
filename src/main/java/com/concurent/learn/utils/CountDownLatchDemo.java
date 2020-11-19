package com.concurent.learn.utils;

import java.util.concurrent.CountDownLatch;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/14
 */
public class CountDownLatchDemo {

    private static CountDownLatch latch = new CountDownLatch(300);

    public static void main(String[] args) {

        for (int i=0; i<300;i++){
            int finalI = i;
            new Thread(()->{
                System.out.println("await-"+finalI+"号线程执行 await()");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(finalI+"号线程从await唤醒");
            }).start();
        }
        for (int i=0; i<300;i++){
            int finalI = i;
            new Thread(()->{
                System.out.println("count-"+finalI+"号线程执行 await()");
                latch.countDown();
                System.out.println(finalI+"号线程执行coutdown完毕");
            }).start();
        }

    }
}
