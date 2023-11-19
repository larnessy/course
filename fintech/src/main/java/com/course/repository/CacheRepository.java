package com.course.repository;

import com.course.model.additional.DoubleLinkedList;
import com.course.model.additional.DoubleLinkedNode;
import com.course.model.entity.WeatherEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CacheRepository {

    private final Map<String, DoubleLinkedNode<WeatherEntity>> cacheMap;
    private final DoubleLinkedList<WeatherEntity> doubleLinkedList;
    private final int maxSize;
    private final int maxIdleTimeInMinutes;
    private final Lock commonLock;

    public CacheRepository(int maxSize, int maxIdleTimeInMinutes,
                           DoubleLinkedList<WeatherEntity> doubleLinkedList) {
        this.maxSize = maxSize;
        this.maxIdleTimeInMinutes = maxIdleTimeInMinutes;
        cacheMap = new HashMap<>(maxSize + 2, 1.0f);
        this.doubleLinkedList = doubleLinkedList;
        this.commonLock = new ReentrantLock();
    }

    public Optional<WeatherEntity> get(String cityName) {
        commonLock.lock();
        try {
            DoubleLinkedNode<WeatherEntity> node = cacheMap.getOrDefault(cityName, null);

            if (node != null) {
                if (isExpired(node.getValue().getDateTime())) {
                    cacheMap.remove(cityName);
                    doubleLinkedList.removeNode(node);
                } else {
                    doubleLinkedList.moveToHead(node);
                    return Optional.of(node.getValue());
                }
            }

            return Optional.empty();
        } finally {
            commonLock.unlock();
        }
    }

    public boolean add(WeatherEntity weatherEntity) throws NullPointerException {
        if (isExpired(weatherEntity.getDateTime())) {
            return false;
        }

        commonLock.lock();
        try {
            String cityName = weatherEntity.getCity().getName();
            DoubleLinkedNode<WeatherEntity> node = cacheMap.getOrDefault(cityName, null);

            if(node == null) {
                node = new DoubleLinkedNode<>();

                cacheMap.put(cityName, node);
                doubleLinkedList.addNodeToHead(node);

                if (cacheMap.size() > maxSize) {
                    DoubleLinkedNode<WeatherEntity> deletedNode = doubleLinkedList.popTail();
                    cacheMap.remove(deletedNode.getValue().getCity().getName());
                }

            } else {
                doubleLinkedList.moveToHead(node);
            }

            node.setValue(weatherEntity);

            return true;
        } finally {
            commonLock.unlock();
        }
    }

    private boolean isExpired(LocalDateTime localDateTime) {
        LocalDateTime maxForAdding = LocalDateTime.now().minusMinutes(maxIdleTimeInMinutes);
        return localDateTime.isBefore(maxForAdding);
    }

    public int size() {
        return cacheMap.size();
    }

    public void clear() {
        cacheMap.clear();
        doubleLinkedList.clear();
        System.gc();
    }
}

