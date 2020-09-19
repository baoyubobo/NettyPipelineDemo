package com.byb.netty.utils;

import com.byb.netty.constant.Const;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 自定义数据生成器
 *
 * @author yubooo
 */
public class MsgBuilder {
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
     * @param name 固定6字节
     * @param age  固定4 字节
     * @param city 固定10 字节
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] buildPersonMsgBytes(String name, int age, String city) throws Exception {

        if (Const.PERSON_NAME_LEN != name.length() || Const.PERSON_CITY_LEN != city.length()) {
            System.out.println("Data is illegal !");
            throw new Exception("Data is illegal !");
        }

        byte[] nameBytes = name.getBytes();
        byte[] ageBytes = intToByteArray(age);
        byte[] cityBytes = city.getBytes();

        byte[] prefixMarkBytes = Const.PREFIX_MARK.getBytes();
        byte[] bodyLenBytes = intToByteArray(Const.PERSON_NAME_LEN + Const.PERSON_AGE_LEN + Const.PERSON_CITY_LEN);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(prefixMarkBytes);
        os.write(bodyLenBytes);
        os.write(nameBytes);
        os.write(ageBytes);
        os.write(cityBytes);
        return os.toByteArray();
    }


    /**
     * int类型转换为byte[]数组
     *
     * @param x
     * @return byte[]
     */
    private static byte[] intToByteArray(int x) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (x >> 24);
        bytes[1] = (byte) (x >> 16);
        bytes[2] = (byte) (x >> 8);
        bytes[3] = (byte) (x >> 0);
        return bytes;
    }
}
