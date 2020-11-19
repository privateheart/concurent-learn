package com.concurent.learn.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * describe:
 *              ThreadLocal 线程隔离 的成员变量的使用
 * @author huyi
 * @date 2020/10/25
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> integerThreadLocal;
    private static ThreadLocal<String> stringThreadLocal;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            integerThreadLocal = new ThreadLocal<>();
            stringThreadLocal = new ThreadLocal<>();
            integerThreadLocal.set(99);
            stringThreadLocal.set("t1");
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            String s = stringThreadLocal.get();
            Integer integer = integerThreadLocal.get();
            System.out.println("t2线程 threadLocal 初始值  string:"+s + " integer:" + integer);
            stringThreadLocal.set("t2");
            integerThreadLocal.set(100);
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1000);
        t2.start();

    }
}
