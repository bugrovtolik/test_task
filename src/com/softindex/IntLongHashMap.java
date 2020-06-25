package com.softindex;

import java.util.Arrays;

public class IntLongHashMap {
    private final int FREE = Integer.MIN_VALUE;
    private int size;
    private int threshold;
    private int[] keys;
    private long[] values;

    public IntLongHashMap(int size, double fillFactor) {
        threshold = (int) (size * fillFactor);
        keys = new int[size];
        values = new long[size];
        Arrays.fill(keys, FREE);
    }

    public IntLongHashMap(int size) {
        this(size, 0.75);
    }

    public IntLongHashMap() {
        this(16, 0.75);
    }

    public long get(int key) {
        int i = index(hash(key));
        while (true) {
            if (i == keys.length) {
                i = 0;
            }
            if (keys[i] == FREE) {
                throw new RuntimeException("No such key!");
            }
            if (keys[i] == key) {
                return values[i];
            }
            i++;
        }
    }

    public void put(int key, long value) {
        if (key == FREE) throw new RuntimeException("Can't use Integer.MIN_VALUE as a key!");

        int i = index(hash(key));
        while (true) {
            if (i == keys.length) {
                i = 0;
            }
            if (keys[i] == FREE) {
                keys[i] = key;
            }
            if (keys[i] == key) {
                values[i] = value;
                size++;
                if (size >= threshold) {
                    rehash();
                }
                return;
            }
            i++;
        }
    }

    private void rehash() {
        threshold <<= 1;

        final int[] oldKeys = keys;
        final long[] oldValues = values;
        keys = new int[oldKeys.length << 1];
        values = new long[oldKeys.length << 1];
        size = 0;

        Arrays.fill(keys, FREE);
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != FREE) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }

    public int size() {
        return size;
    }

    private int hash(int key) {
        return (key >> 15) ^ key;
    }

    private int index(int hash) {
        return Math.abs(hash) % keys.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != FREE) {
                if (sb.length() > 1) {
                    sb.append(",").append(" ");
                }
                sb.append(keys[i]).append("=").append(values[i]);
            }
        }

        return sb.append("}").toString();
    }
}

