package containers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Person{
    String id;
    String name;

    public int hashCode(){
        return id.hashCode();
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public boolean equals(Object obj){
        Person p = (Person) obj;
        if(p.id.equals( this.id ))
            return true;
        else
            return false;
    }
}
public class Test2 {
    public static void test2(){
        System.out.println( "Use String as key:" );
        HashMap<Person, String> hm = new HashMap<Person, String>(  );
        Person p1 = new Person( "222", "name1" );
        Person p2 = new Person( "333", "name2" );

        hm.put( p1, "address1" );
        p1.id = "333";
        System.out.println( p1.hashCode() );
        System.out.println( p2.hashCode() );
        System.out.println( hm.get( p2 ) );
        /*hm.put( p2, "address2" );
        Iterator iter = hm.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            Person key = (Person)entry.getKey();
            String val = (String)entry.getValue();
            System.out.println( "key=" + key + " value=" + val );
        }*/
    }
    public static void main(String[] args){
        test2();
    }
}
