package netty.bio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class GroupChatServer {
    private ServerSocketChannel listenChannel;
    private Selector selector;
    private static final int PORT = 6667;

    //初始化工作
    public GroupChatServer() {
        try{
            listenChannel = ServerSocketChannel.open();
            selector = Selector.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress( PORT );
            listenChannel.bind( inetSocketAddress );
            listenChannel.configureBlocking( false );
            listenChannel.register( selector, SelectionKey.OP_ACCEPT );
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void listen(){
        try{
            while(true){
                int count = selector.select(  );
                if(count > 0){ //有事件处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for(SelectionKey key:selectionKeys){
                        //监听到accept
                        if(key.isAcceptable()){
                            SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                            channel.configureBlocking( false );
                            channel.register( selector, SelectionKey.OP_READ );
                            System.out.println( channel.getRemoteAddress() + " 上线 " );
                        }
                        //监听到read
                        if(key.isReadable()){
                           //处理读，专门写方法
                            readData( key );
                        }

                        //删除key，防止重复处理
                        selectionKeys.remove( key );
                    }
                }else{
                    System.out.println( "等待中..." );
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println( "finally..." );
        }
    }

    public void readData(SelectionKey selectionKey){
        try {
            SocketChannel channel = (SocketChannel)selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate( 1024 );
            int count = channel.read( buffer );
            if(count > 0){
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端：" + msg);
                //向其他客户端转发消息
                System.out.println( "服务器转发消息：" );
                Set<SelectionKey> keys = selectionKey.selector().keys();
                for(SelectionKey key:keys){
                    ByteBuffer buffer2 = ByteBuffer.wrap(  msg.getBytes() );
                    //不等于自己
                    if(!key.equals( selectionKey )){
                        try{
                            Channel targetChannel = key.channel();
                            //属于SocketChannel的实例
                            if(targetChannel instanceof SocketChannel){

                                    ((SocketChannel)targetChannel).write( buffer2 );
                            }
                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    }
                }
                buffer.clear();
            }
        } catch (IOException e) {
            System.out.println( "被关闭" );
            selectionKey.cancel();
            SocketChannel channel = (SocketChannel)selectionKey.channel();
            try{
                channel.close();

            }catch(IOException e2){
                e2.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
