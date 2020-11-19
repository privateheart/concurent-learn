package com.concurent.learn.threaduseexaple;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * describe:
 *       三个线程分别处理 每个请求的 不同的校验逻辑，
 *       用多个线程处理 线程的处理逻辑。
 *
 *       利用 LinkedBlockingQueue 做 生产者消费者模型， 上一个链，就是下一个链的请求生产者
 *
 * @author huyi
 * @date 2020/10/21
 */
public class PreProcessor extends Thread implements IRequestProcessor{

    //阻塞队列
    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<Request>();

    private  IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PreProcessor() {
    }

    public PreProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public void process(Request requset) {
        //TODO 验证请求参数
        requests.add(requset);
//        nextProcessor.process(requset); //我们用线程 来做
    }

    @Override
    public void run() {
        while (!isFinish){
            try {
                Request request = requests.take();
                //真正的处理逻辑
                System.out.println("PreProcessor 处理"  + request);
                if (nextProcessor!=null){
                    nextProcessor.process(request);
                }            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown(){
        isFinish = true;
    }
}
