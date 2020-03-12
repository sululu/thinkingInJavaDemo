package io;

import java.io.*;

public class BufferedInputStreamTest {
    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream( "test2.txt" );
        BufferedInputStream data = new BufferedInputStream( in );
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        data.mark( 1 );
        System.out.println( data.markSupported() );
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());

        data.reset();
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());
        data.reset();
        System.out.println( (char)data.read());
        System.out.println( (char)data.read());

    }
}
