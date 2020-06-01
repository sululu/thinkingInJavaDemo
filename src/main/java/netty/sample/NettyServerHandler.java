package netty.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 说明
 * 1. 我们自定义一个Handler，需要继承Netty规定好的某个HandlerAdapter(规范)
 * 2.这时我们自定义一个Handler，才能称为一个Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //将msg转成ByteBuf
        //ByteBuf是Netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送消息是：" + buf.toString( CharsetUtil.UTF_8 ));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //writeAndFlush 是 write + flush
        //将数据写入到缓冲并刷新
        //一般讲，我们对这个发送的数据进行一个编码

        ctx.channel().eventLoop().schedule( new Runnable() {
            @Override
            public void run() {

                ctx.channel().writeAndFlush( Unpooled.copiedBuffer( "hello，客户端~喵喵喵喵", CharsetUtil.UTF_8 ) );
            }
        } ,2, TimeUnit.SECONDS);

        ctx.channel().eventLoop().execute( new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("阻塞20秒，Begin...");
                    TimeUnit.SECONDS.sleep( 20 );
                    System.out.println("阻塞20秒，End...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.channel().writeAndFlush( Unpooled.copiedBuffer( "hello，客户端~喵喵", CharsetUtil.UTF_8 ) );
            }
        } );

        ctx.channel().eventLoop().execute( new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("阻塞10秒，Begin...");
                    TimeUnit.SECONDS.sleep( 10 );
                    System.out.println("阻塞10秒，End...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ctx.channel().writeAndFlush( Unpooled.copiedBuffer( "hello，客户端~喵喵喵", CharsetUtil.UTF_8 ) );
            }
        } );

        ctx.channel().writeAndFlush( Unpooled.copiedBuffer( "hello，客户端~喵", CharsetUtil.UTF_8 ) );

        System.out.println("test");
    }

    //处理异常，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

}
