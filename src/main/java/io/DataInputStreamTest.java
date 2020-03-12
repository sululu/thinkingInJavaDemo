package io;

import java.io.*;

public class DataInputStreamTest {
    public static void main(String[] args)throws IOException{
       FileInputStream in = new FileInputStream( "test2.txt" );
       DataInputStream data = new DataInputStream( in );
       System.out.println( data.readBoolean() );
       System.out.println( data.readChar() );
       System.out.println( data.readDouble() );
    }
}
