package nettty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class ServerSocketChannelTest {

    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind( new InetSocketAddress( 7777 ) );
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking( false );
        serverSocketChannel.register( selector, SelectionKey.OP_ACCEPT );

        while(true){
            int i = selector.select();
            if(i > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey key:selectionKeys){
                    if(key.isAcceptable()){
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        socketChannel.configureBlocking( false );
                        socketChannel.register( selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024) );
                    }
                    if(key.isReadable()){
                        ByteBuffer buffer =  (ByteBuffer)key.attachment();
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read( buffer );
                        System.out.println(  new String( buffer.array() ) );
                        buffer.clear();

                    }
                    selectionKeys.remove( key );
                }
            }
        }
    }
}
