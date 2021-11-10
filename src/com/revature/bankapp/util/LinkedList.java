package com.revature.bankapp.util;

public class LinkedList<T> implements List<T> {

    private int size;
    private Node<T> head; // implicitly null
    private Node<T> tail = null; // you can explicitly declare them as null, but it's not required.

    @Override
    public boolean add(T data) {

        // Not required, as some data structures do allow for null values.
        if (data == null) {
            return false;
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail = tail.nextNode = newNode;
        }

        size++;

        return true;

    }

    @Override
    public boolean contains(T data) {

        Node<T> runner = head;
        while (runner != null) {
            if (runner.data.equals(data)) {
                return true;
            }
            runner = runner.nextNode;
        }

        // No node with matching data was found
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(T data) {
        Node<T> currentNode = head;
        if(currentNode.data.equals(data))
            return true;
        while(currentNode.nextNode != null) {
            if(currentNode.nextNode.data.equals(data)) {
                currentNode.nextNode = currentNode.nextNode.nextNode;
                return true;
            } else {
                currentNode = currentNode.nextNode;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {

        Node<T> currentNode = head;
        if(index < 0 || index >= size) {
            throw new RuntimeException("Provided value is out of bounds");
        }
        for(int i = 0; i < index; i++) {
            currentNode = currentNode.nextNode;
        }
        return currentNode.data;
    }

    private static class Node<T> {
        T data;
        Node<T> nextNode;

        public Node(T data) {
            this.data = data;
        }
    }

}
