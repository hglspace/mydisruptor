package com.hgldp.web.disruptor.demo1;

import com.lmax.disruptor.EventFactory;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 15:28
 **/
public class LongEventFactory implements EventFactory {


    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
