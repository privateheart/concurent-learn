package com.concurent.learn.InterviewQuestions.printABC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * describe:
 * 来自阿里
 * 有A，B，C三个线程，A线程输出A，B线程输出B， C线程输出C，要求，同时启动三个线程, 按顺序输出ABC，循环10次。
 *
 * 思路：通过一个 共享 的AtomicInteger ,与3取模，三个线程同时判断  0就打印A,1打印B,2打印C
 * @author huyi
 * @date 2020/11/21
 */
public class PrintABC {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        new PrintThread(atomicInteger,"A",0).start();
        new PrintThread(atomicInteger,"B",1).start();
        new PrintThread(atomicInteger,"C",2).start();
    }

    private static class PrintThread extends Thread {
        private AtomicInteger num;
        private int mod;
        private String printStr;
        public PrintThread(AtomicInteger num,String printStr,int mod) {
            this.num = num;
            this.mod = mod;
            this.printStr = printStr;
        }
        @Override
        public void run() {
            while (num.get()<30){
                if (num.get()%3==mod){
                    System.out.print(printStr);
                    num.incrementAndGet();
                }
            }
        }
    }
}
