package com.concurent.learn.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/15
 */
public class FutureDemo implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureDemo futureDemo = new FutureDemo();
        FutureTask<String> futureTask = new FutureTask<>(futureDemo);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }

    @Override
    public String call() throws Exception {
        return "Helo is me!";
    }
}
