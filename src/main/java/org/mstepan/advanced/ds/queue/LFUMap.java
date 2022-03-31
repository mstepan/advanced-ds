package org.mstepan.advanced.ds.queue;

import static org.mstepan.advanced.ds.util.PreConditions.checkArgument;

/**
 * Least frequently used map implementation.
 * During eviction process keys with the same frequencies will be evicted in LRU (least recently used) order.
 */
public class LFUMap<K, V> {

    private final int capacity;
    private int size;
    private final FrequencyNode root = new FrequencyNode(1);

    public LFUMap(int capacity) {
        checkArgument(capacity > 0 && capacity <= 1_000_000, "Capacity should be in range [%d; %d]", 1, 1_000_000);
        this.capacity = capacity;
    }

    public boolean add(K key, V value) {
        return false;
    }

    public V get(K key) {
        return null;
    }

    public int size() {
        return size;
    }

    private static class FrequencyNode<U> {

        final int freq;
        FrequencyNode next;
        FrequencyNode prev;

        FrequencyNode(int freq) {
            this.freq = freq;
        }
    }


}
