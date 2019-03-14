package com.hgldp.web.disruptor.demo4;

import com.lmax.disruptor.RingBuffer;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 22:04
 **/
public class Producer {

    private RingBuffer<Order> ringBuffer;


    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String data){

        long seq = ringBuffer.next();
        try {
            Order order = ringBuffer.get(seq);

            order.setId(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ringBuffer.publish(seq);
        }
    }
}
