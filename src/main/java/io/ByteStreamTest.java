package io;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteStreamTest{
    public static void main(String args[]) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(  );
        while(out.size() != 10){
            out.write( System.in.read() );
        }

        byte b[] = out.toByteArray();
        System.out.println( "Print the content" );

        for(int x = 0; x < b.length; x++){
            //打印字符
            System.out.print( (char)b[x] + "  " );
        }
        System.out.println(  );
        int c;
        ByteArrayInputStream in = new ByteArrayInputStream( b );
        System.out.println( "Converting characters to Upper case " );
        for(int y = 0; y < 1; y++){
            while((c = in.read()) != -1){
                System.out.println( Character.toUpperCase( (char)c ) );
            }
            in.reset();
        }
    }
}