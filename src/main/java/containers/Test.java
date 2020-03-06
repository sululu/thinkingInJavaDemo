package containers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Test {
    public static void test1(){
        System.out.println( "Use user defined class as key:" );
        HashMap<String, String> hm  = new HashMap<String, String>(  );
        hm.put( "aaa", "bbb" );
        hm.put( "aaa", "ccc" );
        Iterator iterator = hm.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            String val = (String)entry.getValue();
            System.out.println( key + " " + val );
        }
    }

    public static void main(String[] args){
        test1();
    }
}
