package com.concurent.learn.InterviewQuestions.mutitask;



/**
 * describe:
 * 来自阿里：
 * 「请寻求最优解，不要只是粗暴wait（）」
 *  有一个总任务A，分解为子任务A1 A2 A3 ...，任何一个子任务失败后要快速取消所有任务，请写程序模拟。
 *
 * 思路：
 * 1、主线程 结束，守护线程自动结束
 * 2、主线程通过循环 判断 4个子任务的线程的 isInterrupted()，判断是否有子任务执行失败
 *
 * @author huyi
 * @date 2020/11/17
 */
public class MutiTaskDemo {
    public static void main(String[] args) {
        task();
    }

    public static void task() {
        long start = System.currentTimeMillis();

        // 初始化任务数量
        Integer taskSize = 100;
        Integer n = taskSize / 4;

        // 假设拆分为4个子任务A1 A2 A3 A4,每个子任务分250个工作量
        Task task = new Task(n);
        Thread a1 = new Thread(task, "a1___周总");
        Thread a2 = new Thread(task, "a2___张总");
        Thread a3 = new Thread(task, "a3___索莱尔");
        Thread a4 = new Thread(task, "a4___飞狐");

        // 设置四个线程为主线程的守护线程
        a1.setDaemon(true);
        a2.setDaemon(true);
        a3.setDaemon(true);
        a4.setDaemon(true);

        // 开始跑
        a1.start();
        a2.start();
        a3.start();
        a4.start();

        while (true) {
            // 如果线程都执行完毕，则退出执行
//            if (Thread.State.TERMINATED.equals(a1.getState()) ||
//                    Thread.State.TERMINATED.equals(a2.getState()) &&
//                    Thread.State.TERMINATED.equals(a3.getState()) &&
//                    Thread.State.TERMINATED.equals(a4.getState())) {
//                System.out.println("任务执行完毕");
//                return;
//            }
            if (a1.isInterrupted() || a2.isInterrupted() || a3.isInterrupted() || a4.isInterrupted()){
                System.out.println("任务执行失败");
                return;
            }
            if (Thread.State.TERMINATED.equals(a1.getState()) &&
                    Thread.State.TERMINATED.equals(a2.getState()) &&
                    Thread.State.TERMINATED.equals(a3.getState()) &&
                    Thread.State.TERMINATED.equals(a4.getState())) {
                System.out.println("任务执行完毕");
                return;
            }
            // 多线程需要控制时间，如果时间超过一分钟，则抛出异常，停止所有线程工作
            long end = System.currentTimeMillis();
            if (end - start > 60000L) {
                throw new RuntimeException("超时拉，停止所有进程");
            }
        }

    }
    private static  class Task implements Runnable {
        // 分配的任务数量
        private Integer taskNum;

        // 只能初始化执行，不能用set方法修改修改
        public Task(Integer taskNum) {
            this.taskNum = taskNum;
        }

        public Integer getTaskNum() {
            return taskNum;
        }

        // 执行任务，打印线程名称和序号
        @Override
        public void run() {
            int err = (int) (Math.random()*taskNum);
            for (int i = 0; i <= taskNum; i++) {
                if (Thread.currentThread().isInterrupted()){
                    return;
                }
                try {
                    // 为了便于观察，每个任务执行一次休息300毫秒
                    Thread.sleep(300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 可以在这个地方加代码，演示线程出异常，导致所有线程停止的情况
                if (i == err) {
                    System.out.println(Thread.currentThread().getName()+"出错了");
                    Thread.currentThread().interrupt();
                    return;
//                    LockSupport.park(this);
                }
                System.out.println(Thread.currentThread().getName() + i);
            }
        }

    }
}
