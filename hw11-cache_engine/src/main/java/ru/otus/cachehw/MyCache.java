package ru.otus.cachehw;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> weakHashMap;

    private final Map<String, HwListener<K, V>> listeners;

    private MyCache() {
        this.weakHashMap = new WeakHashMap<>();
        this.listeners = new HashMap<>();
    }

    private MyCache(int count) {
        this.weakHashMap = new WeakHashMap<>(count);
        this.listeners = new HashMap<>();
    }

    static MyCache getInstance() {
        return new MyCache<>();
    }

    static MyCache getInstance(int count) {
        return new MyCache<>(count);
    }

    @Override
    public void put(K key, V value) {
        this.weakHashMap.put(key, value);
        if(this.listeners.containsKey("CREATE")) {
            HwListener<K,V> handler = this.listeners.get("CREATE");
            handler.notify(key, value, "CREATE");
        }
    }

    @Override
    public void remove(K key) {
        V value = this.weakHashMap.remove(key);
        if(this.listeners.containsKey("DELETE")) {
            HwListener<K,V> handler = this.listeners.get("DELETE");
            handler.notify(key, value, "DELETE");
        }
    }

    @Override
    public V get(K key) {
        V value = this.weakHashMap.get(key);
        if(this.listeners.containsKey("READ")) {
            HwListener<K,V> handler = this.listeners.get("READ");
            handler.notify(key, value, "READ");
        }
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.listeners.remove(listener);
    }
}
