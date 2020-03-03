package net.mindview.util;

import java.util.*;

public class CountingMapData extends AbstractMap<Integer, String> {
    private int size;
    private static String[] chars = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split( " " );

    public CountingMapData(int size) {
        if(size < 0) this.size = 0;
        this.size = size;
    }

    private class Entry implements Map.Entry<Integer, String>{
        int index;

        public Entry(int index) {
            this.index = index;
        }

        public Integer getKey() {
            return index;
        }

        public String getValue() {
            return chars[index % chars.length] + Integer.valueOf( index / chars.length );
        }

        public String setValue(String value) {
            throw new UnsupportedOperationException(  );
        }

        @Override
        public boolean equals(Object o) {
            return Integer.valueOf( index ).equals( o );
        }

        @Override
        public int hashCode() {
            return Integer.valueOf( index ).hashCode();
        }
    }
    public Set<Map.Entry<Integer, String>> entrySet() {
        HashSet<Map.Entry<Integer, String>> entries = new HashSet<Map.Entry<Integer, String>>(  );
        for(int i = 0; i < size; i++)
            entries.add( new Entry( i ) );
        return entries;
    }

    public static void main(String[] args){
        System.out.println( new CountingMapData( 60 ) );
    }
}
