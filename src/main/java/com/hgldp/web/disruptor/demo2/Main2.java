package com.hgldp.web.disruptor.demo2;

import com.lmax.disruptor.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @program:demo
 * @description:使用workerPool做消息处理器
 * @author:hgl
 * @crate:2019-02-26 17:39
 **/
public class Main2 {

    public static void main(String[] args) {
        final int ringBufferSize = 1024;

        int Thread_num = 4;

        final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        },ringBufferSize,new YieldingWaitStrategy());

        ExecutorService executorService = Executors.newFixedThreadPool(Thread_num);

//        创建sequenceBarrier
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        WorkHandler<Trade> handler = new TradeHandler();

        WorkerPool<Trade> workerPool = new WorkerPool<Trade>(ringBuffer,
                sequenceBarrier,new IgnoreExceptionHandler(),handler);

        workerPool.start(executorService);

        for(int i = 0;i< 8;i++){

            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random()* 9999);
            ringBuffer.publish(seq);
        }

        try {
            Thread.sleep(1000);
            workerPool.halt();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
