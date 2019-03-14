package com.hgldp.web.disruptor.demo1;

import com.lmax.disruptor.EventHandler;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 15:30
 **/
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {

        System.out.println("序列号："+sequence + "，数据的值："+event.getValue());
    }
}
