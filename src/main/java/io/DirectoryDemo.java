package io;

import net.mindview.util.PPrint;

import java.io.File;

public class DirectoryDemo {
    public static void main(String[] args){
        //All directories:
        PPrint.pprint(Directory.walk( "." ).dirs);
        //All files beginning with 'T'
        for(File file: Directory.local( ".",  "t.*"))
            System.out.println( file );

        System.out.println( "----------------------" );
        //All Java files begining with 'T'
        for(File file:Directory.walk( ".", "T.*\\.java" ))
            System.out.println( file );
        System.out.println( "=======================" );
        //Class files containing "Z" or "z":
        for(File file:Directory.walk( ".", ".*[Zz].*\\.class" ))
            System.out.println( file );
    }
}
