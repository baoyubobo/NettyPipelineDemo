package com.byb.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * ServerChannelInitializer
 *
 * @author yubooo
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 1. 基于长度动态切分出完整一帧数据
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 2, 4));

        // 2. 将byte[]消息解码成模型对象
        pipeline.addLast(new MsgDecoder());

        // 3. 使用模型对象进一步处理业务逻辑
        pipeline.addLast(new ServerHandler());
    }
}
