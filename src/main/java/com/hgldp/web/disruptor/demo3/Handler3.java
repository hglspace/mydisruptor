package com.hgldp.web.disruptor.demo3;

import com.lmax.disruptor.EventHandler;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 20:57
 **/
public class Handler3 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {

        System.out.println("h3线程的名字："+Thread.currentThread().getName());

    }
}
