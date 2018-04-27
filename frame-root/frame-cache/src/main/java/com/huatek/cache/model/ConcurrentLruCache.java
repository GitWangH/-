package com.huatek.cache.model;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLruCache<KEY, VALUE> implements LruCache<KEY,VALUE> {

    private final ReentrantLock lock = new ReentrantLock ();


    private final Map<KEY, VALUE> map = new ConcurrentHashMap<> ();
    private final Deque<KEY> queue = new LinkedList<> ();
    
    public Collection<VALUE> getAllValue(){
    	return map.values();
    }
    
	public Iterator<KEY> getAllKey(){
    	return queue.iterator();
    }
    
    private final int limit;

    public KEY getLastKey(){
    	if(queue.size()>0){
    		return queue.getLast();
    	}
    	return null;
    }
    public ConcurrentLruCache ( int limit ) {
        this.limit = limit;
    }

    @Override
    public void put ( KEY key, VALUE value ) {
        VALUE oldValue = map.put ( key, value );
        if ( oldValue != null ) {
            removeThenAddKey ( key );
        } else {
            addKey ( key );
        }
        if (map.size () > limit) {
            map.remove ( removeLast() );
        }
    }


    @Override
    public VALUE get ( KEY key ) {
        removeThenAddKey ( key );
        return map.get ( key );
    }


    private void addKey(KEY key) {
        lock.lock ();
        try {
            queue.addFirst ( key );
        } finally {
            lock.unlock ();
        }


    }

    private KEY removeLast( ) {
        lock.lock ();
        try {
            final KEY removedKey = queue.removeLast ();
            return removedKey;
        } finally {
            lock.unlock ();
        }
    }

    private void removeThenAddKey(KEY key) {
        lock.lock ();
        try {
            queue.removeFirstOccurrence ( key );
            queue.addFirst ( key );
        } finally {
            lock.unlock ();
        }

    }

    private void removeFirstOccurrence(KEY key) {
        lock.lock ();
        try {
            queue.removeFirstOccurrence ( key );
        } finally {
            lock.unlock ();
        }

    }


    @Override
    public VALUE getSilent ( KEY key ) {
        return map.get ( key );
    }

    @Override
    public VALUE remove ( KEY key ) {
        removeFirstOccurrence ( key );
        return map.remove ( key );
    }

    @Override
    public int size () {
        return map.size ();
    }

    public String toString () {
        return map.toString ();
    }
    
    public void clear(){
    	map.clear();
    	queue.clear();
    }
}
