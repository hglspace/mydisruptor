package com.hgldp.web.disruptor.demo2;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

import java.util.UUID;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 16:26
 **/
public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade>{


    @Override
    public void onEvent(Trade event) throws Exception {
        //        这里做具体的消费逻辑
        event.setId(UUID.randomUUID().toString());
        System.out.println(event.getId());

    }

    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        onEvent(event);
    }
}
