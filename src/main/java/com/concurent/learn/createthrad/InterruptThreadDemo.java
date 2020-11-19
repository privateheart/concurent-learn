package com.concurent.learn.createthrad;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/10/24
 */
public class InterruptThreadDemo extends Thread{
    private int i;
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println("Test:"+i++);
        }
    }

    public static void main(String[] args) {
        InterruptThreadDemo stopThreadDemo = new InterruptThreadDemo();
        stopThreadDemo.start();
        stopThreadDemo.interrupt();
    }


}
