package ru.otus.cachehw;

import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы

    private WeakHashMap<K, V> weakHashMap = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        this.weakHashMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        this.weakHashMap.remove(key);
    }

    @Override
    public V get(K key) {
        return this.weakHashMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
            
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {

    }
}
