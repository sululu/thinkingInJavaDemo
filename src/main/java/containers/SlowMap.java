package containers;

import net.mindview.util.Countries;

import java.util.*;

public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<K>(  );
    private List<V> values = new ArrayList<V>(  );

    public V put(K key, V value){
        V oldValue = get(key);
        if(!keys.contains(key)){
            keys.add(key);
            values.add( value );
        }else{
            values.set(keys.indexOf( key ), value);
        }
        return oldValue;
    }
    public V get(Object key){
        if(!keys.contains( key ))
            return null;
        return values.get(keys.indexOf( key ));
    }

    static class MapEntry<K, V> implements Map.Entry<K, V>{
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public int hashCode(){
            return (key == null ? 0 : key.hashCode()) ^ (value==null ? 0 :value.hashCode());
        }

        public boolean equals(Object o){
            if(!(o instanceof  MapEntry)) return false;
            MapEntry me = (MapEntry)o;
            return (key == null ? me.getKey() == null : key.equals( me.getKey() ) && (value == null)? me.getValue() == null : value.equals( me.getValue() ));
        }
        public String toString(){
            return "["+key+": "+value+"]";
        }
    }
    public Set<Entry<K, V>> entrySet() {
       Set<Map.Entry<K, V >> set = new HashSet<Entry<K, V >>(  );
       Iterator<K> ki = keys.iterator();
       Iterator<V> vi = values.iterator();
       while(ki.hasNext())
           set.add( new MapEntry<K, V>(ki.next(), vi.next()) );
       return set;
    }

    public static void main(String[] args){
        SlowMap<String, String> m = new SlowMap<String, String>();
        m.putAll( Countries.capitals( 15 ) );
        System.out.println( m );
        System.out.println( m.get("ALGERIA") );
        System.out.println( m.entrySet() );
    }
}
