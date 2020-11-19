package com.concurent.learn.createthrad;

import java.util.concurrent.TimeUnit;

/**
 * describe:
 *             下面的程序不会终止
 * @author huyi
 * @date 2020/10/24
 */
public class InterruptDemo implements Runnable{
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){//false
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {//JVM层面抛出的异常，触发了 线程的复位 ->false，睡眠200s,不代表线程 终止
                //抛出这个异常时，会触发线程复位，
                // 是否终止线程，再由次决定
                e.printStackTrace();
                //让线程本身自己在这里重新决定是否停止。
//                Thread.currentThread().interrupt();
            }
        }
        System.out.println("processor is end !");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new InterruptDemo());
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();// 有作用 true,但是被 catch InterruptedException时触发了 复位
        //由于 线程在sleep,你从外部触发interrupt，导致java.lang.InterruptedException: sleep interrupted
    }
}
