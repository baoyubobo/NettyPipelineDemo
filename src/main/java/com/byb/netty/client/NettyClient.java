package com.byb.netty.client;


import io.netty.bootstrap.Bootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NettyClient
 *
 * @author yubooo
 */
public class NettyClient {
    /**
     * host
     */
    private final String host;

    /**
     * port
     */
    private final int port;

    /**
     * 构造函数
     *
     * @param host host
     * @param port port
     */
    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 开始连接
     */
    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ClientChannelInitializer());

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.out.println("Client ===> 连接服务端成功");
            }
            // channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // workerGroup.shutdownGracefully();
        }
    }
}
