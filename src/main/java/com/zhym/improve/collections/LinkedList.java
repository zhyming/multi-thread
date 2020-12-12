package com.zhym.improve.collections;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/11 0011 23:31
 */
public class LinkedList<E> {

    private Node<E> first;

    private final Node<E> NULL = null;

    private static final String PLAIN_NULL = "null";

    private int size;

    private LinkedList() {
        this.first = NULL;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public static <E> LinkedList<E> of(E... elements) {
        final LinkedList<E> linkedList = new LinkedList<>();
        if (elements.length > 0) {
            for (E e : elements) {
                linkedList.addFirst(e);
            }
        }
        return linkedList;
    }

    public LinkedList<E> addFirst(E e) {
        final Node<E> newNode = new Node<>(e);
        newNode.next = first;
        this.size ++;
        this.first = newNode;
        return this;
    }

    public boolean contains(E e) {
        Node<E> current = first;

        while (current != null) {
            if (current.value == e) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public E removeFirst() {
        if (this.isEmpty())
            throw new NoElementException("The linked list is empty.");
        Node<E> node = first;
        first = node.next;
        size --;
        return node.value;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "[]";
        }else {
            StringBuilder builder = new StringBuilder("[");
            Node<E> current = first;
            while (current != null) {
                builder.append(current.toString()).append(",");
                current = current.next;
            }
            builder.replace(builder.length() -1, builder.length(), "]");
            return builder.toString();
        }
    }

    static class NoElementException extends RuntimeException {
        public NoElementException(String msg) {
            super(msg);
        }
    }

    private static class Node<E> {
        E value;
        Node<E> next;
        public Node(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            if (null != value) {
                return value.toString();
            }

            return PLAIN_NULL;
        }
    }

    public static void main(String[] args) {
        final LinkedList<String> list = LinkedList.of("HELLO", "张三", "李四", "王五", "小红", "小花");
        list.addFirst("JAVA").addFirst("C++").addFirst("JS");

        System.out.println(list.size);
        System.out.println(list.contains("小花1"));
        System.out.println("===============");
        System.out.println(list.toString());

        while (!list.isEmpty()) {
            System.out.println(list.removeFirst());
        }
        System.out.println(list.getSize());
        System.out.println(list.isEmpty());

    }
}
