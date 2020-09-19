package com.byb.netty.utils;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * ByteBufUtils
 *
 * @author yubooo
 */
public final class ByteBufUtils {

    /**
     * 获取 ByteBuf 的一段切片, 默认从 readerIndex 开始
     *
     * @param in     原始 ByteBuf
     * @param length length
     * @return ByteBuf
     */
    public static ByteBuf getSlice(ByteBuf in, int length) {
        return getSlice(in, in.readerIndex(), length);
    }

    /**
     * 获取 ByteBuf 的一段切片
     *
     * @param in         原始 ByteBuf
     * @param startIndex startIndex
     * @param length     length
     * @return ByteBuf
     */
    public static ByteBuf getSlice(ByteBuf in, int startIndex, int length) {

        // 1. 推荐使用 ByteBuf.slice() 函数，因为该函数不会创建新的ByteBuf对象，不会改变RefCnt， 所以也不需要负责管理ByteBuf对象的释放
        ByteBuf bodyByteBuf = in.slice(startIndex, length);

        // 2. 设置原始 ByteBuf 跳过指定长度, 否则会重复读取这部分消息
        in.skipBytes(length);

        // 3. 返回切片，此切片后续不需要主动释放
        return bodyByteBuf;
    }

    /**
     * 从 ByteBuf 中读取 String 类型数据, 默认从 readerIndex 开始
     *
     * @param in     原始 ByteBuf
     * @param length length
     * @return String
     */
    public static String getStringValue(ByteBuf in, int length) {
        return getStringValue(in, in.readerIndex(), length);
    }

    /**
     * 从 ByteBuf 中读取 String 类型数据
     *
     * @param in         原始 ByteBuf
     * @param startIndex startIndex
     * @param length     length
     * @return String
     */
    public static String getStringValue(ByteBuf in, int startIndex, int length) {

        // 1. 读取值
        String value = in.toString(startIndex, length, Charset.defaultCharset());

        // 2. 设置原始 ByteBuf 跳过指定长度, 否则会重复读取这部分消息
        in.skipBytes(length);
        return value;
    }


    /**
     * 另一种方式, 从 ByteBuf 中读取 String 类型数据, 默认从 readerIndex 开始
     *
     * @param in     原始 ByteBuf
     * @param length length
     * @return String
     */
    public static String getStringValue2(ByteBuf in, int length) {
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        return new String(bytes);
    }

    /**
     * 私有构造函数
     */
    private ByteBufUtils() {
    }
}
