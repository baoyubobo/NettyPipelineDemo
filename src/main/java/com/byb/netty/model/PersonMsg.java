package com.byb.netty.model;

import lombok.Data;

@Data
public class PersonMsg {
    private MsgHeader header;
    private MsgBody body;

    public PersonMsg(MsgHeader header, MsgBody body) {
        this.header = header;
        this.body = body;
    }
}
