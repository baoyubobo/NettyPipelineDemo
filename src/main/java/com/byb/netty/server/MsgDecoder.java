package com.byb.netty.server;


import com.byb.netty.constant.Const;
import com.byb.netty.model.MsgBody;
import com.byb.netty.model.MsgHeader;
import com.byb.netty.model.PersonMsg;
import com.byb.netty.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * MsgDecoder
 *
 * @author yubooo
 */
public class MsgDecoder extends ByteToMessageDecoder {
    /**
     * 消息头在一帧消息中的起始位置
     */
    private static final int HEADER_START_INDEX = 0;

    /**
     * 消息头所占节数
     */
    private static final int HEADER_LEN = Const.PREFIX_MARK_LEN + Const.BODY_LENGTH_LEN;

    /**
     * 消息体在一帧消息中的起始位置
     */
    private static final int BODY_START_INDEX = HEADER_START_INDEX + HEADER_LEN;

    /**
     * 消息解码器，将字节流解析转换为模型对象
     *
     * @param ctx ctx
     * @param in  in
     * @param out out
     * @throws Exception Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 字节流不满足完整一帧的长度时，直接返回，等待完整的一帧
        if (Const.FRAME_LEN != in.readableBytes()) {
            System.out.println("Msg frame length error.");
            return;
        }

        // 标记完整一帧开始位置
        in.markReaderIndex();

        // 开始解析字节流
        try {

            // 1. 获取消息头切片，并解析
            ByteBuf headerByteBuf = getHeader(in);
            MsgHeader msgHeader = parseHeaderInfo(headerByteBuf);
            System.out.println("MsgDecoder MsgHeader ===> " + msgHeader.toString());

            // 2. 获取消息体切片，并解析
            ByteBuf bodyByteBuf = getBody(in, msgHeader.getBodyLen());
            MsgBody msgBody = parseBodyInfo(bodyByteBuf);
            System.out.println("MsgDecoder MsgBody ===> " + msgBody.toString());

            // 3. 构建模型对象，传递给后续 pipeline 处理
            out.add(new PersonMsg(msgHeader, msgBody));

        } catch (Exception e) {
            // 打印异常
            System.out.println("MsgDecoder exception : " + e);

        } finally {
            // 无论解析完整一帧报文时是否出现异常，最终都需跳过完整一帧的长度，否则会影响后续的解析
            in.resetReaderIndex();
            in.skipBytes(Const.FRAME_LEN);
        }
    }

    /**
     * 截取一帧消息中的 header
     *
     * @param in in
     * @return ByteBuf
     */
    private ByteBuf getHeader(ByteBuf in) {
        return ByteBufUtils.getSlice(in, HEADER_START_INDEX, HEADER_LEN);
    }

    /**
     * 截取一帧消息中的 body
     *
     * @param in in
     * @return ByteBuf
     */
    private ByteBuf getBody(ByteBuf in, int bodyLen) {
        return ByteBufUtils.getSlice(in, BODY_START_INDEX, bodyLen);
    }


    /**
     * 解析消息头，返回模型对象
     *
     * @param headerByteBuf headerByteBuf
     * @return MsgHeader
     */
    private MsgHeader parseHeaderInfo(ByteBuf headerByteBuf) {

        // 2字节消息头前缀标识##
        String prefixMark = ByteBufUtils.getStringValue(headerByteBuf, Const.PREFIX_MARK_LEN);

        // 4字节 int 类型消息体长度
        int bodyLen = headerByteBuf.readInt();

        return new MsgHeader(prefixMark, bodyLen);
    }

    /**
     * 解析消息体，返回模型对象
     *
     * @param bodyByteBuf bodyByteBuf
     * @return MsgHeader
     */
    private MsgBody parseBodyInfo(ByteBuf bodyByteBuf) {

        // 6字节 Person.name
        String name = ByteBufUtils.getStringValue(bodyByteBuf, Const.PERSON_NAME_LEN);

        // 4字节 int 类型 Person.age
        int age = bodyByteBuf.readInt();

        // 10字节 Person.city
        String city = ByteBufUtils.getStringValue(bodyByteBuf, Const.PERSON_CITY_LEN);

        return new MsgBody(name, age, city);
    }
}
