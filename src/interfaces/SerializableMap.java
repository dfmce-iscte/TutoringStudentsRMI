package interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableMap<K, V> implements Serializable {

    public class MapEntry implements Serializable {
        private K key;
        private V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        @Override
        public String toString() { return key.toString() + "=" + value.toString(); }

    } 

    private List<MapEntry> map;

    public SerializableMap() {
        map = new ArrayList<MapEntry>();
    }

    public List<MapEntry> getMap() {
        return map;
    }

    public List<K> keys() {
        List<K> keys = new ArrayList<K>();
        for (MapEntry entry : map) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    public boolean containsKey(K key) {
        for (MapEntry entry : map) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        for (MapEntry entry : map) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void put(K key, V value) {
        remove(key);
        map.add(new MapEntry(key, value));
    }

    public void remove(K key) {
        if (containsKey(key))
            map.remove(get(key));
    }

    @Override
    public String toString() {
        String string = "";
        for (MapEntry entry : map) {
            string += entry.toString() + ", ";
        }
        return string;
    }
}
