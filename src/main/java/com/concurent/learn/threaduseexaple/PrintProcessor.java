package com.concurent.learn.threaduseexaple;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/10/21
 */
public class PrintProcessor extends Thread implements IRequestProcessor{

    //阻塞队列
    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    private  IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PrintProcessor() {
    }

    public PrintProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public void process(Request requset) {
        requests.add(requset);
    }

    @Override
    public void run() {
        while (!isFinish){
            try {
                Request request = requests.take();
                //真正的处理逻辑
                System.out.println("PrintProcessor 处理"  + request);
                if (nextProcessor!=null){
                    nextProcessor.process(request);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown(){
        isFinish = true;
    }
}
