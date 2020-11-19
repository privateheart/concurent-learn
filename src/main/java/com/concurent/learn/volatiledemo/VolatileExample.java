package com.concurent.learn.volatiledemo;

/**
 * describe:
 *           Happens-Before 模型
 *    volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
 * @author huyi
 * @date 2020/10/23
 */
public class VolatileExample {
    static int a =0;
    static volatile boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            //1 一定 happens before 2
            a = 1;//1
            flag = true; //2
        });

        Thread thread2 = new Thread(() -> {
            //如果flag = ture,说明 2 happens before 3
            //根据传递规则， 那么 1 一定 happens before 4, 所以 i一定等于1;
            //3 一定 happens before 4
            if (flag) {//3
                int i = a;//4
                System.out.println("thread 2中， i=" + i);
            }
        });
        thread1.start();
        thread2.start();
    }
}
