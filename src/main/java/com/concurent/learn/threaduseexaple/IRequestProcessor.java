package com.concurent.learn.threaduseexaple;

/**
 * describe:
 *          定义一个接口，用来做责任链的基类
 * @author huyi
 * @date 2020/10/21
 */
public interface IRequestProcessor {

    void process(Request requset);

}
