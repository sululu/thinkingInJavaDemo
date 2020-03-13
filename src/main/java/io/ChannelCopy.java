package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCopy {
    private static final int BSIZE = 1024;
    public static void main(String[] args) throws Exception{
        try{
            FileChannel in = new FileInputStream("data.txt" ).getChannel();
            FileChannel out = new FileOutputStream( "data2.txt" ).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate( BSIZE );

            while(in.read( buffer ) != -1){
                buffer.flip();  //Prepare for writing
                out.write( buffer );
                buffer.clear(); //Prepare for reading
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
