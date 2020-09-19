package com.byb.netty.model;

import lombok.Data;

@Data
public class MsgBody {
    private String name;
    private int age;
    private String city;

    public MsgBody(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }
}
