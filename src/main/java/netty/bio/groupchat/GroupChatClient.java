package netty.bio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6667;
    private String username;

    public GroupChatClient() {
        try{
            socketChannel = SocketChannel.open(  );
            InetSocketAddress inetSocketAddress = new InetSocketAddress( HOST, PORT );
            socketChannel.connect( inetSocketAddress );
            socketChannel.configureBlocking( false );

            selector = Selector.open();
            socketChannel.register( selector , SelectionKey.OP_READ );

            username = socketChannel.getLocalAddress().toString().substring( 1 );

            System.out.println(username + " is ok");



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendInfo(String info){
            info = username + "说：" + info;
        try {
            socketChannel.write( ByteBuffer.wrap( info.getBytes() ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo(){
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        try {
            int num = selector.select(2000);
            if(num > 0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for(SelectionKey key:selectionKeys){
                   if(key.isReadable()){
                       SocketChannel channel = (SocketChannel) key.channel();
                       int cnt = channel.read( buffer );
                       if(cnt > 0){
                           System.out.println(new String(buffer.array()));
                           buffer.clear();
                       }
                   }
                   selectionKeys.remove( key );
                }
            }else{

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatClient chatClient = new GroupChatClient();
        new Thread(  ){
            @Override
            public void run() {
                while(true){
                    chatClient.readInfo();
                    try{
                        TimeUnit.MILLISECONDS.sleep( 3000 );
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner( System.in );
        while(scanner.hasNext()){
            String s = scanner.nextLine();
            chatClient.sendInfo( s );
        }
    }
}
