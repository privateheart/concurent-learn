package com.concurent.learn.createthrad;

import java.util.concurrent.*;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/10/23
 */
public class CreateThreadDemo {

    public static void main(String[] args) {
//        createThread1();
//        createThread2();
        createThread3();
    }

    public static void createThread1(){
        RunnalbeDemo1 threadDemo = new RunnalbeDemo1();
        Thread thread1 = new Thread(threadDemo);
        thread1.setName("Runnable-Thread-1");
        Thread thread2 = new Thread(threadDemo);
        thread2.setName("Runnable-Thread-1");
        thread1.start();
        thread2.start();
    }

    public static void createThread2(){
        ThreadDemo2 thread1 = new ThreadDemo2();
        ThreadDemo2 thread2 = new ThreadDemo2();
        thread1.start();
        thread2.start();
    }

    public static void createThread3(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> result = executorService.submit(new CallableDemo3());
        try {
            String s = result.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class RunnalbeDemo1 implements Runnable{
        @Override
        public void run() {
            System.out.println("实现 Runnable 接口，创建线程，启动了");
        }
    }


    private static class ThreadDemo2 extends Thread {
        @Override
        public void run() {
            System.out.println("继承 Thread 接口，创建线程，启动了");
        }
    }

    //带返回值的线程
    private static class CallableDemo3 implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "Callable-String";
        }
    }
}
