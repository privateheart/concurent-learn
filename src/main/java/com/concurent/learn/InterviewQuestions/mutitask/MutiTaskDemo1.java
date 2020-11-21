package com.concurent.learn.InterviewQuestions.mutitask;

/**
 * describe:
 * 来自阿里：
 * 「请寻求最优解，不要只是粗暴wait（）」
 *  有一个总任务A，分解为子任务A1 A2 A3 ...，任何一个子任务失败后要快速取消所有任务，请写程序模拟。
 *
 * 思路
 *  1.所用任务 通过一个 volatile的 flag 来线程间通信，是否有其他子任务执行失败,执行失败则结束线程
 * @author huyi
 * @date 2020/11/18
 */
public class MutiTaskDemo1 {

    public static void main(String[] args) {
        MasterTask masterTask = new MasterTask(100);
        BranchTask branchTask = new BranchTask(masterTask, 4);

        Thread t1 = new Thread(branchTask,"a1___周总");
        Thread t2 = new Thread(branchTask,"a2___张总");
        Thread t3 = new Thread(branchTask,"a3___索莱尔");
        Thread t4 = new Thread(branchTask, "a4___飞狐");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        while (true){
            if (masterTask.getFlag()!=0){
                System.out.println("有个分支任务失败了 整体失败");
                return;
            }
            if (Thread.State.TERMINATED.equals(t1.getState()) &&
                    Thread.State.TERMINATED.equals(t2.getState()) &&
                    Thread.State.TERMINATED.equals(t3.getState()) &&
                    Thread.State.TERMINATED.equals(t4.getState()) ){
                System.out.println("任务执行成功了！");
                return;
            }
        }
    }

    private static class MasterTask {
        private volatile int flag = 0;
        private int tasknum;
        public MasterTask(int tasknum) {
            this.tasknum = tasknum;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }

    private static class BranchTask implements Runnable {
        private MasterTask masterTask;
        private int branchTaskNum;
        public BranchTask(MasterTask masterTask,int splitnum) {
            this.masterTask = masterTask;
            this.branchTaskNum = masterTask.tasknum/splitnum;
        }
        @Override
        public void run() {
            int err = (int) (Math.random()*branchTaskNum);
            for (int i=0; i< branchTaskNum; i++){
                if (masterTask.getFlag()!=0){
                    System.out.println("有分支任务执行失败了，在下："+Thread.currentThread().getName()+"也停止运行");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "->  开始执行分支任务-"+ i);
                try {
                    Thread.sleep(300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i==err){
                    masterTask.setFlag(1);
                    System.out.println(Thread.currentThread().getName() + "->  开始执行分支任务-"+ i + "失败了");
                }
            }
        }
    }
}
