package com.byb.netty.model;

import lombok.Data;

@Data
public class MsgHeader {
    private String prefixMark;
    private int bodyLen;

    public MsgHeader(String prefixMark, int bodyLen) {
        this.prefixMark = prefixMark;
        this.bodyLen = bodyLen;
    }
}
