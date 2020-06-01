package netty.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {

        //创建BossGroup和WorkerGroup

        //说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup只是处理连接请求，真正和客户端业务处理，会交给workerGroup完成
        //3. 两个都是无限循环

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程进行设置
            bootstrap.group( bossGroup, workerGroup )   //设置两个线程组
                    .channel( NioServerSocketChannel.class ) //使用nioServerSocketChannel作为服务器
                    // 的通道实现
                    .option( ChannelOption.SO_BACKLOG, 128 ) //设置线程队列得到的连接个数
                    .childOption( ChannelOption.SO_KEEPALIVE,  true ) //设置保持活动连接状态
                    .childHandler( new  ChannelInitializer<SocketChannel>() { //创建一个通道初始化对象（匿名对象）

                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast( new NettyServerHandler() );
                        }
                    } ); //给我们的workerGroup的EventLoop的对应的管道设置处理器
            System.out.println("....服务器 is ready ...");
            try {
                //绑定一个端口并且同步，生成了一个channelFuture对象
                ChannelFuture future = bootstrap.bind( 6668 ).sync();

                future.addListener( new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("绑定成功");
                        }else{
                            System.out.println("绑定失败");
                        }
                    }
                } );

                //对关闭通道进行监听
                future.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
