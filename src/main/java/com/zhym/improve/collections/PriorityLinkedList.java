package com.zhym.improve.collections;

/**
 * @description:
 * @author: zhym
 * @time: 2020/12/12 0012 0:15
 */
public class PriorityLinkedList<E extends Comparable<E>> {

    private Node<E> first;

    private final Node<E> NULL = null;

    private static final String PLAIN_NULL = "null";

    private int size;

    private PriorityLinkedList() {
        this.first = NULL;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public static <E extends Comparable<E>> PriorityLinkedList<E> of(E... elements) {
        final PriorityLinkedList linkedList = new PriorityLinkedList<>();
        if (elements.length > 0) {
            for (E e : elements) {
                linkedList.addFirst(e);
            }
        }
        return linkedList;
    }

    public PriorityLinkedList<E> addFirst(E e) {
        final Node<E> newNode = new Node<>(e);
        Node<E> previous = NULL;
        Node<E> current = first;
        while (current != null && e.compareTo(current.value) > 0) {
            previous = current;
            current = current.next;
        }

        if (previous == NULL) {
            first = newNode;
        }else {
            previous.next = newNode;
        }

        newNode.next = current;
        size ++;
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
        final PriorityLinkedList<Integer> linkedList = PriorityLinkedList.of(10, 1, -10, 0, 100, 88, 90, 2);
        System.out.println(linkedList.toString());
    }

}