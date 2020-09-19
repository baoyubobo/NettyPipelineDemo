package com.byb.netty.constant;

/**
 * Const
 * @author yubooo
 */
public class Const {
    /**
     * 自定义数据格式
     * <p>
     * |<------ 2 ------>|<------- 4 ------->|<----- 6 ------->|<----- 4 ----->|<---------- 10 --------->|
     * |                 |                   |                 |               |                         |
     * |-----------------|-------------------|-----------------|---------------|-------------------------|
     * |   prefixMark    |     bodyLen       |      name       |      age      |           city          |
     * |      ##         |       20          |                 |               |                         |
     * |-----------------|-------------------|-----------------|---------------|-------------------------|
     * |                                     |                                                           |
     * |<------------ header---------------->|<---------------------- body ----------------------------->|
     *
     * /

    /**
     * 消息前缀头标识， 固定为 ##
     */
    public static final String PREFIX_MARK = "##";

    /**
     * 完整一帧消息字所占节数
     */
    public static final int FRAME_LEN = 26;

    /**
     * 消息前缀头标识所占字节数
     */
    public static final int PREFIX_MARK_LEN = 2;

    /**
     * 消息体长度所占字节数
     */
    public static final int BODY_LENGTH_LEN = 4;

    /**
     * person.name 所占字节数
     */
    public static final int PERSON_NAME_LEN = 6;

    /**
     * person.age 所占字节数
     */
    public static final int PERSON_AGE_LEN = 4;

    /**
     * person.city 所占字节数
     */
    public static final int PERSON_CITY_LEN = 10;
}
