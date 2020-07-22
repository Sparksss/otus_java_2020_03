package ru.otus.cachehw;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> weakHashMap = new WeakHashMap<>();

    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    private ReferenceQueue<HwListener<K, V>> listenerReferenceQueue = new ReferenceQueue<>();

    @Override
    public void put(K key, V value) {
        this.clearRemovedObjects();
        if(this.weakHashMap.containsKey(key)) {
            this.notifyAllListeners(key, value, "UPDATE");
        } else {
            this.notifyAllListeners(key, value, "CREATE");
        }
        this.weakHashMap.put(key, value);

    }

    @Override
    public V get(K key) {
        this.clearRemovedObjects();
        V value = this.weakHashMap.get(key);
        this.notifyAllListeners(key, value, "READ");
        return value;
    }

    @Override
    public void remove(K key) {
        this.clearRemovedObjects();
        V value = this.weakHashMap.remove(key);
        this.notifyAllListeners(key, value, "DELETE");

    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(new WeakReference<HwListener<K, V>>(listener, this.listenerReferenceQueue));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        if(listener != null) this.listeners.remove(listener);
    }

    private void clearRemovedObjects() {
        WeakReference listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();
        while (listenerWeakRef != null) {
            listeners.remove(listenerWeakRef);
            listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();
        }
    }

    private void notifyAllListeners(K key, V value, String action) {
        for(WeakReference wr : this.listeners) {
            HwListener<K, V> handler =(HwListener<K, V>) wr.get();
            handler.notify(key, value, action);
        }
    }
}
