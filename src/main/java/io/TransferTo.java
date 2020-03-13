package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TransferTo {
    public static void main(String[] args) throws Exception{
        FileChannel in = new FileInputStream("data.txt" ).getChannel();
        FileChannel out = new FileOutputStream( "data2.txt" ).getChannel();
        try{
            in.transferTo( 0, in.size(), out );
        }catch(IOException e){

        }
    }
}
