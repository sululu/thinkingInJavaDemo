package netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelTest {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open(  );
        InetSocketAddress inetSocketAddress = new InetSocketAddress( "127.0.0.1", 7777 );
        socketChannel.configureBlocking( false );
        if(!socketChannel.connect( inetSocketAddress )){
            while(!socketChannel.finishConnect()){
                System.out.println("等待连接...");
            }
        }

        String str = "hello，尚硅谷~";

        ByteBuffer buffer = ByteBuffer.wrap( str.getBytes() );
        socketChannel.write( buffer );
        System.in.read();
    }
}
