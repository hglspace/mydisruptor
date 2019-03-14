package com.hgldp.web.disruptor.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-28 16:12
 **/
public class TestBuffer {

    public static void main(String[] args) {
        Random random = new Random();
        ByteBuf byteBuf = Unpooled.buffer(64);

        System.out.println(byteBuf.writableBytes());
        byteBuf.writeInt(random.nextInt());
        System.out.println(byteBuf.writableBytes());




    }
}
