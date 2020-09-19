package com.byb.netty.client;

import com.byb.netty.utils.MsgBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ClientHandler
 *
 * @author yubooo
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 建立连接后发送自定义消息
     *
     * @param ctx ctx
     * @throws Exception Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client ===> channel Active");
        System.out.println("Client ===> start send message ...");
        sendMessage(ctx);
        System.out.println("Client ===> end send message.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client ===> channel read msg :" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client ===> exception Caught");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client ===> channel Inactive");
    }

    /**
     * 向Server发送自定义消息
     *
     * @param ctx ctx
     */
    private void sendMessage(ChannelHandlerContext ctx) {
        try {
            for (int i = 1; i <= 5; i++) {

                byte[] bytes1 = MsgBuilder.buildPersonMsgBytes("Tom   ", i, "Beijing   ");
                ByteBuf byteBuf1 = Unpooled.copiedBuffer(bytes1);
                ctx.writeAndFlush(byteBuf1);

                byte[] bytes2 = MsgBuilder.buildPersonMsgBytes("Alice ", i, "Shanghai  ");
                ByteBuf byteBuf2 = Unpooled.copiedBuffer(bytes2);
                ctx.writeAndFlush(byteBuf2);

            }
        } catch (Exception e) {
            System.out.println("Send message error.");
            e.printStackTrace();
        }
    }
}