package ru.clevertec.cache;

import ru.clevertec.cacheInterface.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LFUCache<K,V> implements Cache<K,V> {
    private Map<K,Node> valueMap;
    private Map<K,Integer> countMap;
    private TreeMap<Integer,DoubleLinkedList> freqMap;
    private int size;

    public LFUCache(int cacheSize){
        this.size = cacheSize;
        valueMap = new HashMap<>();
        countMap = new HashMap<>();
        freqMap = new TreeMap<>();
    }

    @Override
    public boolean containsKey(K key) {
        return valueMap.containsKey(key);
    }

    @Override
    public V get(K key) {
        if (!valueMap.containsKey(key) || size == 0){
            return null;
        }
        Node nodeToDelete = valueMap.get(key);
        Node node = new Node(key, nodeToDelete.value);
        deleteAndUpdate(key, node, nodeToDelete);
        return valueMap.get(key).getValue();

    }

    @Override
    public void add(K key, V value) {
        if(!valueMap.containsKey(key) && size > 0){
            Node node = new Node(key, value);
            if (valueMap.size() == size){
                int lowCount = freqMap.firstKey();
                Node nodeToDelete = freqMap.get(lowCount).head;
                freqMap.get(lowCount).remove(nodeToDelete);
                removeListIfEmpty(lowCount);

                K keyToDelete = nodeToDelete.getKey();
                valueMap.remove(keyToDelete);
                countMap.remove(keyToDelete);
            }
            freqMap.computeIfAbsent(1, x -> new DoubleLinkedList()).add(node);
            valueMap.put(key,node);
            countMap.put(key,1);
        } else if (size>0) {
            Node node = new Node(key, value);
            Node nodeToDelete = valueMap.get(key);
            deleteAndUpdate(key, node, nodeToDelete);
        }
    }

    @Override
    public V remove(K key) {
        if (valueMap.containsKey(key)){
            Node nodeToDelete = valueMap.get(key);
            int freq = countMap.get(key);
            freqMap.get(freq).remove(nodeToDelete);
            removeListIfEmpty(freq);
            valueMap.remove(key);
            countMap.remove(key);
            return nodeToDelete.getValue();
        }else return null;
    }

    private void deleteAndUpdate(K key, Node node, Node nodeToDelete) {
        int freq = countMap.get(key);
        freqMap.get(freq).remove(nodeToDelete);
        removeListIfEmpty(freq);
        valueMap.remove(key);
        countMap.remove(key);
        valueMap.put(key,node);
        countMap.put(key, freq+1);

        freqMap.computeIfAbsent(freq+1, x-> new DoubleLinkedList()).add(node);
    }

    private void removeListIfEmpty(int freq){
        if(freqMap.get(freq).len() == 0){
            freqMap.remove(freq);
        }
    }
    private class Node{
        private K key;
        private V value;
        Node next;
        Node prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey(){
            return key;
         }

        public V getValue() {
            return value;
        }
    }

    private class DoubleLinkedList{
        private int n;
        private Node head;
        private Node tail;

        public void add(Node node){
            if(head == null){
                head = node;
            }else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
            n++;
        }

        public void remove(Node node){

            if(node.next == null){
                tail = node.prev;
            }else {
                node.next.prev = node.prev;
            }
            if(node.prev == null){
                head = node.next;
            }else {
                node.prev.next = node.next;
            }

            n--;
        }

        public Node head(){
            return head;
        }

        public Node tail(){
            return tail;
        }
        public int len(){
            return n;
        }
    }

}
