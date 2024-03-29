package netty.inboundhandleroutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //加入一个出站的handler，对数据进行一个编码
        pipeline.addLast( new MyLongToByteEncoder() );
        //加入一个自定义的handler，处理业务
        pipeline.addLast( new MyByteToLongDecoder() );
        pipeline.addLast( new MyClientHandler() );
    }
}
