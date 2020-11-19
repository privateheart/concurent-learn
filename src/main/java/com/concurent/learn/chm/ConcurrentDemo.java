package com.concurent.learn.chm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/14
 */
public class ConcurrentDemo {

    private static volatile   int a = 16;

    public static void main(String[] args) throws InterruptedException {
        int b  = a;
        b++;
        System.out.println("a="+a);
        System.out.println("b="+b);

        CountDownLatch countDownLatch = new CountDownLatch(100);
        ConcurrentHashMap<Object, Object> chm = new ConcurrentHashMap<>();
        for (int i=0;i<100;i++){
            int finalI = i;
            new Thread(()-> {
                chm.put("i-" + Math.random(), finalI);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(chm);
    }

}
