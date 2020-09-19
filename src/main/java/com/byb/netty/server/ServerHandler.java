package com.byb.netty.server;

import com.byb.netty.model.PersonMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ServerHandler
 *
 * @author yubooo
 */
public class ServerHandler extends SimpleChannelInboundHandler<PersonMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonMsg msg) throws Exception {

        // 自定义业务逻辑，此出打印PersonMsg对象
        System.out.println("========== MsgHandler ==========");
        System.out.println(msg.toString());
        System.out.println("================================");
    }
}
