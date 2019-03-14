package com.hgldp.web.disruptor.demo1;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 15:31
 **/
public class Main {

    public static void main(String[] args) {



//        创建工厂
        LongEventFactory factory = new LongEventFactory();
//        创建ringbuffer的大小 官方建议是2的n次方
        int ringBufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,ringBufferSize
        , Executors.defaultThreadFactory(),ProducerType.SINGLE,new YieldingWaitStrategy());

        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

//         两种生产者及发布消息的方式
//        LongEventProducer longEventProducer = new LongEventProducer(disruptor.getRingBuffer());

        LongEventProducerWithTranslator longEventProducerWithTranslator = new LongEventProducerWithTranslator(disruptor.getRingBuffer());

        for(int i = 0 ;i < 100;i++){
            longEventProducerWithTranslator.onData(Long.valueOf(i));
        }

//        关闭disruptor，方法会阻塞，直到所有的事件都得到处理
        disruptor.halt();

    }
}
