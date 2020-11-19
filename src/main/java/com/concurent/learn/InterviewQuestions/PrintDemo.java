package com.concurent.learn.InterviewQuestions;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/11/19
 */
public class PrintDemo {
    public static void main(String[] args) throws InterruptedException {

        String lock = "LOCK";
        ThreadM m = new ThreadM(lock);
        ThreadN n = new ThreadN(lock);
        m.setN(n);
        n.setM(m);
        n.setFlag(false);
        m.setFlag(true);//m线程先跑
        n.setStart(false);

        n.start();
        m.start();

    }
}

//
class ThreadM extends Thread{
    private ThreadN n;
    private String lock;
    private boolean flag; //线程唤醒标志状态
    public ThreadM() {
        super();
    }

    public ThreadM(String lock) {
        this.lock = lock;
    }
    public void setN(ThreadN n) {
        this.n = n;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        for(char i = 'A'; i <= 'Z'; i++){
            synchronized (lock) {
                if(flag) {
                    lock.notify();
                }
                flag = !flag;
                this.n.setFlag(flag);
                System.out.print(i);
                try {
                    //打印了A,开始启动 B线程
                    if (i=='A'){
                        this.n.setStart(true);
                    }
                    lock.wait();
                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ThreadN extends Thread{
    private ThreadM m;
    private String lock;
    private boolean flag;
    private boolean start;
    public ThreadN() {
        super();
    }
    public ThreadN(String lock) {
        this.lock = lock;
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public void setM(ThreadM m) {
        this.m = m;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public void run() {
        for(int i = 'A', j=1; i <= 'Z'; i++, j++){
            synchronized (lock) {
                if (!start){
                    //只有m线程执行完了，n线程才会Start
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!flag) {
                    lock.notify();
                }
                flag = !flag;
                this.m.setFlag(flag);
                System.out.print(j);
                try {
                    lock.wait();
//                    lock.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
