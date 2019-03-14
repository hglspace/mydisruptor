package com.hgldp.web.disruptor.demo1;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 16:05
 **/
public class LongEventProducerWithTranslator {

    private RingBuffer<LongEvent> ringBuffer;

//    一个translator可以看作一个事件初化器，publicEvent方法会调用它
    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent,Long> TRANSLATOR = new
            EventTranslatorOneArg<LongEvent, Long>() {
                @Override
                public void translateTo(LongEvent event, long sequence, Long arg0) {
                    event.setValue(arg0);
                }
            };


    public void onData(Long data){
        ringBuffer.publishEvent(TRANSLATOR,data);
    }
}
