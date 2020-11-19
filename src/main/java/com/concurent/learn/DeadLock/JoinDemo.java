package com.concurent.learn.DeadLock;

/**
 * describe:
 *
 *   t.join 会阻塞主线程，等待 t执行完之后，会唤醒 主线程 、
 *   （JVM 源码Thread.cpp里，执行完run方法后会调用，ensure join 方法，
 *      唤醒因为该线程join而阻塞的其他线程）
 *
 * @author huyi
 * @date 2020/10/24
 */
public class JoinDemo {

    private static int i=10;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            i = 30;
            System.out.println("");
        });
        t.start();
        //t线程中的执行的结果对于main 线程可见
        t.join(); //Happens-Before 模型
        System.out.println("i:"+i);
    }
}
