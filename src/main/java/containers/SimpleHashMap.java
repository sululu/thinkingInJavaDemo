package containers;

import net.mindview.util.Countries;

import java.util.*;

public class SimpleHashMap<K,V> extends AbstractMap<K, V> {
    // Choose a prime number for the hash table size,
    // to achieve a uniform distribution:
    static final int SIZE = 997;
    //You can't have a physical array of generics:
    //but you can upcast to one:
    @SuppressWarnings( "unchecked" )
    LinkedList<SlowMap.MapEntry<K, V>>[] buckets = new LinkedList[SIZE];
    public V put(K key, V value){
        V oldValue = null;
        int index = Math.abs( key.hashCode() ) % SIZE;
        if(buckets[index] == null)
            buckets[index] = new LinkedList<SlowMap.MapEntry<K, V>>(  );
        LinkedList<SlowMap.MapEntry<K ,V >> bucket = buckets[index];
        SlowMap.MapEntry<K, V> pair = new SlowMap.MapEntry<K, V>( key, value );
        boolean found = false;
        ListIterator<SlowMap.MapEntry<K, V>> it = bucket.listIterator(  );
        while(it.hasNext()){
            SlowMap.MapEntry<K, V> iPair = it.next();
            if(iPair.getKey().equals( key )){
                oldValue = iPair.getValue();
                it.set(pair);
                found = true;
                break;
            }
        }
        if(!found)
            buckets[index].add( pair );
        return oldValue;
    }

    public V get(Object key){
        int index = Math.abs( key.hashCode() ) % SIZE;
        if(buckets[index] == null) return null;
        for(SlowMap.MapEntry<K, V> iPair: buckets[index])
            if(iPair.getKey().equals( key ))
                return iPair.getValue();
            return null;
    }

    public Set<Map.Entry<K, V>> entrySet(){
        Set<Map.Entry<K, V>> set = new HashSet<Map.Entry<K, V>>(  );
        for (LinkedList<SlowMap.MapEntry<K, V>> bucket:buckets){
            if(bucket == null) continue;
            for(SlowMap.MapEntry<K, V> mpair:bucket)
                set.add(mpair);
        }
        return set;
    }

    public static void main(String[] args){
        SimpleHashMap<String, String> m = new SimpleHashMap<String, String>();
        m.putAll( Countries.capitals( 25 ) );
        System.out.println( m );
        System.out.println( m.get( "ANGOLA" ) );
        System.out.println( m.entrySet() );
    }

}
