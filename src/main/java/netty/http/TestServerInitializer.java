package netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        //加入一个netty提供的httpServerCodec codec [coder - decoder]
        //HttpServerCoder说明
        //1. HttpServerCodec 是 netty提供的http的编解码器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec(  ) );
        //2.增加一个自定义的handler
        pipeline.addLast( "myTestHttpServerHandler", new TestHttpServerHandler() );

    }
}
