package com.hgldp.web.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 15:48
 **/
public class LongEventProducer {

    private RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(long data){
        long sequence = ringBuffer.next();

        try {

            LongEvent event = ringBuffer.get(sequence);
            event.setValue(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

//            publish方法必须被执行，否则出现异常的时候没有执行的话，sequence没有commit，将会堵塞后续的发布操作或者其它的 producer
            ringBuffer.publish(sequence);
        }
    }
}
