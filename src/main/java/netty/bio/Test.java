package netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println("生成服务器连接...");
        ServerSocket serverSocket = new ServerSocket( 6666 );
        ExecutorService exec = Executors.newCachedThreadPool();

        while(true){
            System.out.println("等待连接请求...");
            final Socket socket = serverSocket.accept();

            exec.execute( new Runnable() {
                public void run() {
                    action( socket );
                }
            } );
        }
    }

    public static void action(Socket socket){
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while(true){
                int read = inputStream.read( bytes );
                if(read != -1){
                    System.out.println(new String( bytes, 0, read ));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭链接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
