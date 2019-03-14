package com.hgldp.web.disruptor.demo3;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 21:05
 **/
public class TradePublisher implements Runnable {

    private Disruptor<Trade> disruptor;

    private CountDownLatch latch;

    private final static int Loop = 1000000;

    public TradePublisher(CountDownLatch latch,Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {

        TradeEventTranslator translator = new TradeEventTranslator();
        for(int i = 0 ;i< Loop;i++){
            disruptor.publishEvent(translator);
        }

        latch.countDown();

    }

    class TradeEventTranslator implements EventTranslator<Trade>{

        private Random random = new Random();

        @Override
        public void translateTo(Trade event, long sequence) {
            this.generateTrade(event);
        }

        private Trade generateTrade(Trade trade){
            trade.setPrice(random.nextDouble() * 9999);
            return trade;
        }
    }
}
