package com.concurent.learn.InterviewQuestions.printABC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * describe:
 * 来自阿里
 * 有A，B，C三个线程，A线程输出A，B线程输出B， C线程输出C，要求，同时启动三个线程, 按顺序输出ABC，循环10次。
 *
 * 思路：通过一个 共享  volatile in num ,与3取模，三个线程同时判断  0就打印A,1打印B,2打印C
 * @author huyi
 * @date 2020/11/21
 */
public class PrintABC3 {
    private static volatile int num = 0;
    public static void main(String[] args) {
        Object lock = new Object();
        new PrintThread("A",0).start();
        new PrintThread("B",1).start();
        new PrintThread("C",2).start();
    }
    private static class PrintThread extends Thread {
        private int mod;
        private String printStr;
        public PrintThread(String printStr,int mod) {
            this.mod = mod;
            this.printStr = printStr;
        }
        @Override
        public void run() {
            while (num<30){
                if (num%3==mod && num<30){
                    System.out.print(printStr);
                    num++;
                }
            }
        }
    }
}
