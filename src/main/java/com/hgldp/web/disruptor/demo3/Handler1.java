package com.hgldp.web.disruptor.demo3;

import com.lmax.disruptor.EventHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.*;
import java.nio.charset.Charset;


/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 20:57
 **/
public class Handler1 implements EventHandler<Trade> {

    private ByteBuf byteBuf;
    //private RandomAccessFile randomAccessFile;

    private BufferedOutputStream bufferedOutputStream;

    private byte[] s = (System.nanoTime()+" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"+
            " 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            +" 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，"
            + " 收到消息了，我这是测试啊，东方证券，东方证券，东方证券，越长越好吧，" +
            "开始了，baby"+System.getProperty("line.separator")).getBytes(Charset.forName("utf-8"));
    /*private static RandomAccessFile randomAccessFile;
    static {
        try {
            randomAccessFile = new RandomAccessFile("/Users/zyq/Applications/ringb.txt","rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/


    public Handler1(BufferedOutputStream bufferedOutputStream) {
        byteBuf = Unpooled.buffer(1024*1024*10);
//        this.randomAccessFile = randomAccessFile;
        try {
            this.bufferedOutputStream = bufferedOutputStream;
        }catch (Exception e){

        }

    }


    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
//        System.out.println("h1线程的名字："+Thread.currentThread().getName());
        //System.out.println("byteBuf的可写长度："+byteBuf.writableBytes());

        //System.out.println(s.length);
        //System.out.println("endOfBatch:"+endOfBatch);
        if(byteBuf.writableBytes() > s.length){
            byteBuf.writeBytes(s);
        }else {
            wirterToFile();
            byteBuf.clear();
            byteBuf.writeBytes(s);
        }

        if(endOfBatch){
           // System.out.println("运行了..."+sequence);
            wirterToFile();
        }

    }


    private void wirterToFile(){
        try {
            byte[] b = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(b);
//            randomAccessFile.seek(randomAccessFile.length());
//            randomAccessFile.write(b);

            bufferedOutputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
