package io;

import java.io.*;

public class FileStreamTest {
    public static void main(String[] args){
        try{
            byte bWrite[] = {'a', 'b', 'c', 'd'};
            OutputStream os = new FileOutputStream( "test2.txt" );
            for(int x = 0; x < bWrite.length; x++){
                os.write( bWrite[x] ); // writes the bytes
            }
            os.close();

            InputStream is = new FileInputStream( "test2.txt" );
            int size = is.available();
            for(int i = 0; i < size; i++){
                System.out.print((char)is.read() + " ");
            }
            is.close();
        }catch (IOException e){
            System.out.println( "Exception" );
        }
    }
}
