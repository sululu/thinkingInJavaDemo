package netty.inboundhandleroutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 会根据接收的数据，被调用多次，list
     * ，或者是ByteBuf没有更多的可读字节为止
     * 如果list out不为空，就会将list的内容传递给下一个channelinboundhandler处理，改处理器的方法也会被调用多次
     * @param channelHandlerContext 上下文对象
     * @param byteBuf 入栈的ByteBuf
     * @param list List集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder 被调用");
        //因为long 8个字节，需要判断有8个字节，才能读取一个long
        if(byteBuf.readableBytes() >= 8){
            list.add( byteBuf.readLong() );
        }
    }
}
