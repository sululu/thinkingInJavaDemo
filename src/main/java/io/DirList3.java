package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList3 {
    public static void main(final String[] args){
        File path = new File( "." );
        String[] list;
        if(args.length == 0)
            list = path.list();
        else
            list = path.list( new FilenameFilter() {
                Pattern pattern;
                public boolean accept(File dir, String name) {
                    return Pattern.compile( args[0] ).matcher( name ).matches();
                }
            } );
        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for(String dirItem: list)
            System.out.println( dirItem );
    }
}
