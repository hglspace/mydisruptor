package com.hgldp.web.disruptor.demo3;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * @program:demo
 * @description:
 * @author:hgl
 * @crate:2019-02-26 16:25
 **/
public class Trade {

    private String id;

    private String name;

    private double price;

    private AtomicInteger count = new AtomicInteger(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }
}
