package com.hgldp.web.disruptor.demo4;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 21:46
 **/
public class Consumer implements WorkHandler<Order> {


    private String customerId;

    private static AtomicInteger count = new AtomicInteger(0);

    public Consumer(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void onEvent(Order event) throws Exception {

        System.out.println("当前消费者："+this.customerId + ",消费信息："+event.getId());

        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
