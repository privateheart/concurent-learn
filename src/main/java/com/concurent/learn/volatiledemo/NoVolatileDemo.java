package com.concurent.learn.volatiledemo;

import java.io.File;

/**
 * describe:
 *     为啥不用 volatile的变量，在当前线程中，没有实时感知到 其他线程对它做出的修改
 *      jdk server版本 会有深度优化
 *          while (!stop){
 *                 i++;
 *             }
*           优化成
 *           if(!stop){
 *               while(true){
 *                   i++;
 *               }
 *           }
 *       关闭JIT 优化， -Djava.compiler=NONE
 *
 *   还有中
 * @author huyi
 * @date 2020/10/21
 */
public class NoVolatileDemo {
    //不用 volatile的变量,当前程序不会停止,但是有五种方式可以让他停止
    //while 循环里打印 i，
    //while 循环里加 同步锁
    //while 循环里操作IO new File();
    //原理是 IO和synchronized 中有阻塞的锁机制，Douglee 写的一本书里有 ，锁释放以后，会将主内存的修改的数据 同步的工作线程

    //Thread.sleep() 也会停止线程  原理 ：https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#js-17.3
    // i 放到成员变量，用 volatile修饰，内存屏障
    private static boolean stop = false;
    private volatile static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread thread  = new Thread(()->{
//            int i=0;
            while (!stop){
                i++;
//                System.out.println(i);

//                synchronized (NoVolatileDemo.class){}

//                try {
//                    Thread.sleep(1000); //线程切换后，导致本地线程缓存失效，重新从主内存加载改数据
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                new File("txt.txt");
            }
            System.out.println(i);
        });
        thread.start();
        Thread.sleep(1000);
        stop = true;
    }
}
