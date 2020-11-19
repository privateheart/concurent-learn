package com.concurent.learn.volatiledemo;

/**
 * describe:
 *  加上jvm 参数:
 *  -server -Xcomp -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:CompileCommand=compileonly,*VolatileDemo.*
 *
 *
 * @author huyi
 * @date 2020/10/21
 */
public class VolatileDemo {

    public volatile   static boolean stop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            int i = 0;
            while (!stop){
                i++;
            }
            System.out.println(i);
        });
        thread.start();
        Thread.sleep(1000);
        stop = true;
    }
}
