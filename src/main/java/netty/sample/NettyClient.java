package netty.sample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) {

        //客户但需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端创建的是Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group( group ) //设置客户端通道的实现类
                    .channel( NioSocketChannel.class ) //设置客户端通道的实现类
                    .handler( new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast( new NettyClientHandler() );
                        }
                    } );


            System.out.println("客户端 is ok...");

            try {
                //启动客户端去连接服务器端
                //关于ChannelFuture，要分析，涉及到netty的异步模型
                ChannelFuture channelFuture = bootstrap.connect( "127.0.0.1", 6668 ).sync();

                //给关闭通道进行监听
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            group.shutdownGracefully();
        }

    }
}
