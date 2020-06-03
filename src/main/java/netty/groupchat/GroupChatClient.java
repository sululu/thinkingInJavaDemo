package netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {
    //属性
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group( group )
                    .channel( NioSocketChannel.class )
                    .handler( new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //得到pipeline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //加入相关的handler
                            pipeline.addLast( new StringDecoder(  ) );
                            pipeline.addLast( new StringEncoder(  ) );
                            pipeline.addLast( new GroupChatClientHandler() );
                        }
                    } );


            ChannelFuture future = bootstrap.connect( host, port ).sync();
            Channel channel = future.channel();
            System.out.println("---- " + channel.localAddress() + " ----");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner( System.in );
            while(scanner.hasNextLine()){
                String msg = scanner.nextLine();
                //通过channel发送到服务器端
                channel.writeAndFlush( msg );
            }
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new GroupChatClient( "localhost", 7000 ).run();
    }
}
