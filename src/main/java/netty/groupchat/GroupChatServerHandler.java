package netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
    //定义一个channel组，管理所有的channel
    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static ChannelGroup channels = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE );

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //读取到当前channel
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //获取到当前channel
        Channel channel = channelHandlerContext.channel();
        //这时我们遍历channelGroup，根据不同的清空，回送不同的消息
        channels.forEach( ch->{
            if(channel != ch){ //不是当前channel，转发消息
                ch.writeAndFlush( "[客户]" + channel.remoteAddress() + " 发送了消息 " +  s + "\n");
            }else{ //回显自己发送的消息给自己
                ch.writeAndFlush( "[自己]发送了消息 " + s + "\n");
            }
        } );
    }

    //handlerAdded 表示连接建立，一旦连接，第一个被执行
    //将当前channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户端
        /**
         * 该方法会将channelGroup中所有的channel遍历，并发送消息，我们不需要自己遍历
         */
        channels.writeAndFlush( "[客户端]" + channel.remoteAddress() + "加入聊天"+ sdf.format( new Date(  ) )+"\n" );
        channels.add( channel );
    }

    //表示channel处于活动状态，提示xxx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    //表示channel 处于不活动状态，提示xxx下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 下线了~");
    }

    //表示断开连接，将xxx客户端离开推送给当前在线的客户
    //自己从channels中去掉
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush( "[客户端]" + channel.remoteAddress() + "离开了\n" );
        System.out.println("channelGroup size " + channels.size());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
