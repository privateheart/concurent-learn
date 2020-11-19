package com.concurent.learn.threaduseexaple;

/**
 * describe:
 *
 * @author huyi
 * @date 2020/10/21
 */
public class App {

    static IRequestProcessor requestProcessor;

    public App(){
        PrintProcessor printProcessor = new PrintProcessor();
        SaveProceesor saveProceesor = new SaveProceesor(printProcessor);
        requestProcessor = new PreProcessor(saveProceesor);
        printProcessor.start();
        saveProceesor.start();
        ((PreProcessor) requestProcessor).start();
    }

    public static void main(String[] args) {
        Request request = new Request();
        request.setName("testName");
        request.setMsg("testMsg.....");
        App app = new App();
        requestProcessor.process(request);
    }
}
