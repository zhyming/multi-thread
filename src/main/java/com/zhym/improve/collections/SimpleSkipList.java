package com.zhym.improve.collections;

import java.util.Random;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/12 0012 20:28
 */
public class SimpleSkipList {

    private final static byte HEAD_NODE = (byte)-1;
    private final static byte DATA_NODE = (byte)0;
    private final static byte TAIL_NODE = (byte)-1;

    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;


    public SimpleSkipList() {
        this.head = new Node(null, HEAD_NODE);
        this.tail = new Node(null, TAIL_NODE);
        head.right = tail;
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());

    }

    public Node find(Integer element) {
        Node current = head;
        for (;;) {
            //while (current.right.bit != )
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        private E value;
        private Node up, down, left, right;
        private byte bit;

        private Node(E value, byte bit) {
            this.value = value;
            this.bit = bit;
        }
        private Node(E value) {
            this(value, DATA_NODE);
        }
    }
}
