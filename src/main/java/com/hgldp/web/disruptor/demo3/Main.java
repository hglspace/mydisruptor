package com.hgldp.web.disruptor.demo3;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 20:53
 **/
public class Main {



    public static void main(String[] args) {

        long beginTime = System.currentTimeMillis();
        int bufferSize = 1024;

//        RandomAccessFile randomAccessFile = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            //randomAccessFile = new RandomAccessFile("/Users/zyq/Applications/ringb.txt","rwd");
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("/Users/zyq/Applications/ringb.txt"),1024*1024*10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Disruptor<Trade> disruptor = new Disruptor<Trade>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        },bufferSize,Executors.defaultThreadFactory(), ProducerType.SINGLE,new BusySpinWaitStrategy());


//    使用disruptor创建消费者组handler1,handler2
//    handler1与handler2是并行执行的
//        EventHandlerGroup<Trade> handlerGroup = disruptor.handleEventsWith(new Handler1(randomAccessFile),new Handler2());
//     handler3在handler1和handler2执行完之后再执行
//        handlerGroup.then(new Handler3());


        /*
        * 顺序执行 handler1-->handler2---handler3
        * */

       /* disruptor.handleEventsWith(new Handler1());
        disruptor.handleEventsWith(new Handler2());
        disruptor.handleEventsWith(new Handler3());*/

        disruptor.handleEventsWith(new Handler1(bufferedOutputStream));
        /*disruptor.handleEventsWith(new Handler2());
        disruptor.handleEventsWith(new Handler3());*/
        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);

        executorService.submit(new TradePublisher(latch,disruptor));

        try {
            latch.await();

            disruptor.shutdown();

            executorService.shutdown();
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("总耗时："+(System.currentTimeMillis()-beginTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


}
