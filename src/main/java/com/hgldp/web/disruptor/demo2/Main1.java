package com.hgldp.web.disruptor.demo2;

import com.lmax.disruptor.*;

import java.util.concurrent.*;

/*
 * @program:demo
 * @description:使用EventProcessor做消息处理器
 * @author:hgl
 * @crate:2019-02-26 16:29
 **/
public class Main1 {

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
//        创建消息处理器
        BatchEventProcessor<Trade> transProcessor = new BatchEventProcessor<Trade>(
                ringBuffer,sequenceBarrier,new TradeHandler()
        );
//        把消费者的位置信息引用注入到生产者，如果只有一个消费者的情况可以省略
        ringBuffer.addGatingSequences(transProcessor.getSequence());//这里可以添加多个，addGatingSequences接受的是可变参数（新版本）
//        把消息处理器提交到线程池
        executorService.submit(transProcessor);
//        如果存在多个消费者，就重复执行上面的3行代码，把tradeHandler换成其他消费者（老版本）

        Future<?> future = executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                long seq;

                for(int i = 0;i < 10;i++){
                    seq = ringBuffer.next();
                    ringBuffer.get(seq).setPrice(Math.random()*9999);
                    ringBuffer.publish(seq);
                }

                return null;
            }
        });


        try {
            future.get();//等待生产结束

//            等待1秒，等待消费者都消费完成
            TimeUnit.SECONDS.sleep(1);
//            通知事件（或者说消息）处理器可以结束了，（并不是马上结束）
            transProcessor.halt();

            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
