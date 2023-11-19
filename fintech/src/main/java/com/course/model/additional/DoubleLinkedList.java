package com.course.model.additional;

public class DoubleLinkedList<V> {
    private final DoubleLinkedNode<V> head;
    private final DoubleLinkedNode<V> tail;

    public DoubleLinkedList() {
        head = new DoubleLinkedNode<>();
        tail = new DoubleLinkedNode<>();
        head.setPost(tail);
        tail.setPre(head);
    }

    public void addNodeToHead(DoubleLinkedNode<V> node) {
        node.setPre(head);
        node.setPost(head.getPost());

        head.getPost().setPre(node);
        head.setPost(node);
    }

    public void removeNode(DoubleLinkedNode<V> node) {
        DoubleLinkedNode<V> pre = node.getPre();
        DoubleLinkedNode<V> post = node.getPost();

        pre.setPost(post);
        post.setPre(pre);
    }

    public void moveToHead(DoubleLinkedNode<V> node) {
        removeNode(node);
        addNodeToHead(node);
    }

    public DoubleLinkedNode<V> popTail() {
        DoubleLinkedNode<V> res = tail.getPre();
        removeNode(res);
        return res;
    }
}
