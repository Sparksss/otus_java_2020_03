package ru.otus.cachehw;

import java.lang.ref.ReferenceQueue;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> weakHashMap = new WeakHashMap<>();

    private final List<HwListener<K, V>> listeners = new ArrayList<>();

    private ReferenceQueue<HwListener<K, V>> listenerReferenceQueue = new ReferenceQueue<>();

    @Override
    public void put(K key, V value) {
        if(this.weakHashMap.containsKey(key)) {
            this.notifyAllListeners(key, value, "UPDATE");
        } else {
            this.notifyAllListeners(key, value, "CREATE");
        }
        this.weakHashMap.put(key, value);

    }

    @Override
    public V get(K key) {
        V value = this.weakHashMap.get(key);
        this.notifyAllListeners(key, value, "READ");
        return value;
    }

    @Override
    public void remove(K key) {
        V value = this.weakHashMap.remove(key);
        this.notifyAllListeners(key, value, "DELETE");

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        if(listener != null) this.listeners.remove(listener);
    }


    private void notifyAllListeners(K key, V value, String action) {
        for(HwListener<K, V> handler: this.listeners) {
            handler.notify(key, value, action);
        }
    }
}
