package com.concurent.learn.InterviewQuestions.printABC;

public class PrintABC2 {
    public static void main(String[] sss) {
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        ThreadC c = new ThreadC();
        a.setB(b);
        a.setC(c);
        b.setA(a);
        b.setC(c);
        c.setA(a);
        c.setB(b);

        a.start();
        b.start();
        c.start();
    }
}

class ThreadA extends Thread {
    private ThreadB b;
    private ThreadC c;
    private String lock = "LOCK";
    private int t = 0;
    private int n = 1;// 打印的次数

    public void run() {
        for (; n <= 10;) {
            synchronized (lock) {
                if (t % 3 == 0) {
                    System.out.print("A");
                    t = t + 1;
                    this.b.setT(t);
                    this.c.setT(t);
                    n = n + 1;
                }

                try {
                    lock.notifyAll();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void setB(ThreadB b) {
        this.b = b;
    }

    public void setC(ThreadC c) {
        this.c = c;
    }

    public void setT(int t) {
        this.t = t;
    }
}

class ThreadB extends Thread {
    private ThreadA a;
    private ThreadC c;
    private String lock = "LOCK";
    private int t = 0;
    private int n = 1;// 打印的次数

    public void run() {
        for (; n <= 10;) {
            synchronized (lock) {
                if (t % 3 == 1) {
                    t = t + 1;
                    System.out.print("B");
                    this.a.setT(t);
                    this.c.setT(t);
                    n = n + 1;
                }
                try {
                    lock.notifyAll();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void setA(ThreadA a) {
        this.a = a;
    }

    public void setC(ThreadC c) {
        this.c = c;
    }

    public void setT(int t) {
        this.t = t;
    }
}

class ThreadC extends Thread {
    private ThreadA a;
    private ThreadB b;
    private String lock = "LOCK";
    private int t = 0;
    private int n = 1;// 打印的次数

    public void run() {
        for (; n <= 10;) {
            synchronized (lock) {
                if (t % 3 == 2) {
                    t = t + 1;
                    System.out.print("C");
                    this.a.setT(t);
                    this.b.setT(t);
                    n = n + 1;
                }
                try {
                    lock.notifyAll();
                    if(n<10) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setA(ThreadA a) {
        this.a = a;
    }

    public void setB(ThreadB b) {
        this.b = b;
    }

    public void setT(int t) {
        this.t = t;
    }
}