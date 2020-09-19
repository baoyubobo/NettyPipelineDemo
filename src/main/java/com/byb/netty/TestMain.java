package com.byb.netty;

import com.byb.netty.client.NettyClient;
import com.byb.netty.server.Server;

/**
 * TestMain
 * @author yubooo
 */
public class TestMain {
    public static void main(String[] args) {
        try {
            String host = "127.0.0.1";
            int port = 12345;

            // 启动 Sever
            Server server = new Server(port);
            server.run();
            Thread.sleep(1000);

            // 启动 Client，建立连接后向 Sever 发送数据
            NettyClient client = new NettyClient(host, port);
            client.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
