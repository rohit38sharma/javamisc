package com.test.impl;

import com.test.api.KVStore;

public class KVStoreImpl<K,V> implements KVStore<K,V> {

    private static int size;

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    static class Node<K,V> {
        int hash;
        K key;
        V val;
        Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public K getKey(){
            return key;
        }
        public V getValue() {
            return val;
        }
        public int hashcode() {
            return hash;
        }
    }

    Node<K,V>[] table;

    public KVStoreImpl(){
        this.size = 16;
        this.table = new Node[this.size];
    }

    public KVStoreImpl(int size){
        this.size = size;
        this.table = new Node[this.size];
    }

    public V get(K key) {
        Node<K,V>[] tab = table;
        Node<K,V> e;
        int n;
        int h = key.hashCode();
        if(tab!=null && (n = tab.length)>0&&(e = tab[(n-1)&h])!=null){
            do {
                if (e.hash == h && (e.key == key || (e.key != null && key.equals(e.key))))
                    return e.val;
            }while ((e = e.next) != null);
        }
        return null;
    }

    public void put(K key, V value) {
        if (key == null || value == null) throw new NullPointerException();
        int h = key.hashCode();
        if(table==null)
            table = new Node[this.size];
        int n = table.length;
        Node<K,V> e = table[(n-1)&h];
        if(e==null){
            table[(n-1)&h] = new Node<K, V>(h,key,value,null);
        }else{
            synchronized (e){
                Node<K,V> ek = e;
                Node<K,V> eprev = null;
                boolean keyExist = false;
                while(ek!=null){
                    if(ek.hash == h && ek.key == key){
                        keyExist = true;
                        ek.val = value;
                        break;
                    }
                    eprev = ek;
                    ek = ek.next;
                }
                if(!keyExist){
                    eprev.next = new Node<K, V>(h,key,value,null);
                }
            }
        }
    }

    public void delete(K key) {
        if (key == null) throw new NullPointerException();
        int h = key.hashCode();
        Node<K,V>[] tab = table;
        Node<K,V> e;
        int n;
        if(tab!=null&&(n=tab.length)>0&&(e=tab[(n-1)&h])!=null){
            if(e.hash==h && e.key==key){
                tab[(n-1)&h] = null;
            }else{
                synchronized(e){
                    Node<K,V> ek=e.next,eprev=e;
                    while(ek!=null){
                        if(ek.hash==h && ek.key==key){
                            eprev.next = ek.next;
                            break;
                        }
                        eprev = ek;
                        ek = ek.next;
                    }
                }
            }
        }
    }

    public void clear() {
        this.size = 0;
        this.table = null;
    }

    public long size() {
        return size;
    }
}
