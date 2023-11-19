package com.course.model.additional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoubleLinkedNode<V> {
    private V value;
    private DoubleLinkedNode<V> pre;
    private DoubleLinkedNode<V> post;
}
