package com.hgldp.web.disruptor.demo4;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/*
 * @program:demo
 * @description:多个生产者 多个消费者
 * @author:hgl
 * @crate:2019-02-26 21:43
 **/
public class Main {

    public static void main(String[] args) {
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI,

                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },1024*1024,new YieldingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();


        Consumer[] consumers = new Consumer[3];

        for(int i = 0;i<consumers.length;i++){
            consumers[i] = new Consumer("c"+ i);
        }

        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer,
                sequenceBarrier,new IgnoreExceptionHandler(),consumers);

//        多个消费者需要加上这一行，单个消费者就不需要了
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        final CountDownLatch latch = new CountDownLatch(1);

        for(int i = 0 ;i<10;i++){
            final Producer p = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for(int j = 0;j< 10;j++){
                        p.onData(UUID.randomUUID().toString());
                    }
                }
            }).start();

            try {
                Thread.sleep(2000);
                System.out.println("-------开始生产-------");

                latch.countDown();

                Thread.sleep(5000);

                System.out.println("总数："+consumers[0].getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }
}
