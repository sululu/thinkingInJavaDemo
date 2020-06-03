package netty.love;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

//这里TextWebSocketFrame类型，标识一个文本帧（frame）
public class MyServerHandler extends  SimpleChannelInboundHandler<TextWebSocketFrame>{

    private static ChannelGroup channelGroup = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded被调用 " + ctx.channel().remoteAddress());
        channelGroup.add( ctx.channel() );

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用 " + ctx.channel().remoteAddress());
        channelGroup.remove( ctx.channel() );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息 " + textWebSocketFrame.text());
        Channel currentChannel = channelHandlerContext.channel();
        //回复消息
        Iterator<Channel> iterator = channelGroup.iterator();
        while(iterator.hasNext()){
            Channel channel = iterator.next();
            if(!channel.equals( currentChannel ))
                channel.writeAndFlush( new TextWebSocketFrame( "他：" + textWebSocketFrame.text() ) );
            else
                channel.writeAndFlush( new TextWebSocketFrame( "我：" + textWebSocketFrame.text() ) );
        }
    }
}