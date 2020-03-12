package io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataOutputStreamTest {
    public static void main(String[] args) throws IOException {
        FileOutputStream out = new FileOutputStream( "test2.txt" );
        DataOutputStream data = new DataOutputStream( out );
       // data.writeBytes( "This is a test" );
        data.writeBoolean( false );
        data.writeChar( 97 );
        data.writeDouble( 1.1 );
        data.close();

    }
}
