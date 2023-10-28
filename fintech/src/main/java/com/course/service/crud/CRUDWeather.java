package com.course.service.crud;

import com.course.exception.myException.ThereIsAlreadySuchWeather;
import com.course.exception.myException.ThereIsNoCityWithThisId;
import com.course.model.Weather;

public interface CRUDWeather {
    double findTemperatureInCityToday(int id);

    /* В следующих модулях будет БД, где мы нормализуем данные, выделив имя региона и его id в отдельную сущность.
       Пока, учитывая, что данный метод должен так же принимать дату, температуру и прочее — очевидно, проще так.
       Выделение данной сущности на этом этапе не обозначалось, более того требовало обоснования при реализации
       (отметил преподаватель в чате), потому оставляю в таком сыром-кривом виде, полагая, что так и задумано… */
    void addCity(Weather weather) throws ThereIsAlreadySuchWeather;

    // пока так, после добавления Hibernate логика несколько изменится (будет save, который и сохраняет, и обновляет)
    void saveOrUpdate(Weather weather) throws ThereIsAlreadySuchWeather;

    void deleteByCity(int id) throws ThereIsNoCityWithThisId;
}
