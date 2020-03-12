package io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        PipedInputStream pipedInputStream = new PipedInputStream(  );
        PipedOutputStream pipedOutputStream = new PipedOutputStream(  );
        try{
            pipedInputStream.connect( pipedOutputStream );
            //默认一次最多只能写入1024字节
            byte[] data = new byte[1000];
            byte[] store = new byte[20];
            Arrays.fill(data, (byte)1);
            System.out.println( "first writing data" );
            //每次写1000字节数据
            pipedOutputStream.write( data, 0, data.length );
            System.out.println( "finish first writing" );

            int count = 1;

            while(count < 100){
                System.out.println( count + " times read data" );
                pipedInputStream.read(store, 0, store.length); //每次读20字节数据
                System.out.println( count + " times finish reading data" );
                System.out.println( (count+1) + " times write data" );
                pipedOutputStream.write( data ); //每次写1000字节数据
                System.out.println( (count+1) + " times finish writing data" );
                count++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
