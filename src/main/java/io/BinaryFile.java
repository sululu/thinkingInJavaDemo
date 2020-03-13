package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryFile {
    public static byte[] read(File bFile) throws IOException{
        BufferedInputStream in = new BufferedInputStream( new FileInputStream( bFile ) );
        try{
            byte[] data = new byte[in.available()];
            in.read(data);
            return data;
        }catch (IOException e){
            throw new RuntimeException( e );
        }finally {
            in.close();
        }
    }
    public static byte[] read(String bFile) throws IOException{
        return read(new File( bFile ).getAbsoluteFile());
    }
}
