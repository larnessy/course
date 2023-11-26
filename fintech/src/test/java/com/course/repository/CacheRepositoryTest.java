package com.course.repository;

import com.course.model.additional.DoubleLinkedList;
import com.course.model.entity.City;
import com.course.model.entity.WeatherCondition;
import com.course.model.entity.WeatherEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

class CacheRepositoryTest {

    static CacheRepository cacheRepository;
    static DoubleLinkedList<WeatherEntity> doubleLinkedList;

    @BeforeAll
    public static void initialize(){
        doubleLinkedList = new DoubleLinkedList<>();
        cacheRepository = new CacheRepository(3, 10, doubleLinkedList);
    }

    @AfterEach
    public void clear(){
        cacheRepository.clear();
    }

    @Test
    public void testUpdateHeadAndTailValuesOnGet(){
        WeatherEntity weather1 = new WeatherEntity(new City("1"),
                new WeatherCondition(), 1, LocalDateTime.now());
        WeatherEntity weather2 = new WeatherEntity(new City("2"),
                new WeatherCondition(), 1, LocalDateTime.now());
        WeatherEntity weather3 = new WeatherEntity(new City("3"),
                new WeatherCondition(), 1, LocalDateTime.now());
        cacheRepository.add(weather1);
        cacheRepository.add(weather2);
        cacheRepository.add(weather3);

        WeatherEntity headBefore = doubleLinkedList.getHeadValue();
        WeatherEntity tailBefore = doubleLinkedList.getTailValue();
        cacheRepository.get(weather1.getCity().getName());
        WeatherEntity headAfter = doubleLinkedList.getHeadValue();
        WeatherEntity tailAfter = doubleLinkedList.getTailValue();

        assertEquals(headBefore.getCity().getName(), weather3.getCity().getName());
        assertEquals(headAfter.getCity().getName(), weather1.getCity().getName());
        assertEquals(tailBefore.getCity().getName(), weather1.getCity().getName());
        assertEquals(tailAfter.getCity().getName(), weather2.getCity().getName());
    }

    @Test
    public void testRemoveTailAndUpdateHeadValueOnAddAndMaintainingSizeLessMaxSizeWhenExceedsMaxSize(){
        WeatherEntity weather1 = new WeatherEntity(new City("1"),
                new WeatherCondition(), 1, LocalDateTime.now());
        WeatherEntity weather2 = new WeatherEntity(new City("2"),
                new WeatherCondition(), 1, LocalDateTime.now());
        WeatherEntity weather3 = new WeatherEntity(new City("3"),
                new WeatherCondition(), 1, LocalDateTime.now());
        WeatherEntity weather4 = new WeatherEntity(new City("4"),
                new WeatherCondition(), 1, LocalDateTime.now());
        cacheRepository.add(weather1);
        cacheRepository.add(weather2);
        cacheRepository.add(weather3);
        int oldSize = cacheRepository.size();

        WeatherEntity headBefore = doubleLinkedList.getHeadValue();
        WeatherEntity tailBefore = doubleLinkedList.getTailValue();
        cacheRepository.add(weather4);
        WeatherEntity headAfter = doubleLinkedList.getHeadValue();
        WeatherEntity tailAfter = doubleLinkedList.getTailValue();

        assertEquals(oldSize, cacheRepository.size());
        assertEquals(headBefore.getCity().getName(), weather3.getCity().getName());
        assertEquals(headAfter.getCity().getName(), weather4.getCity().getName());
        assertEquals(tailBefore.getCity().getName(), weather1.getCity().getName());
        assertEquals(tailAfter.getCity().getName(), weather2.getCity().getName());
    }
}