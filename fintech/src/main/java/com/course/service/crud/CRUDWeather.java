package com.course.service.crud;

import com.course.exception.myException.ThereIsAlreadySuchWeather;
import com.course.exception.myException.ThereIsNoCityWithThisId;
import com.course.model.Weather;
import com.course.repository.MyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CRUDWeather {

    public double findTemperatureInCityToday(int id) {
        return MyRepository.list.stream().filter(w -> w.getId() == id &&
                        w.getDateTime().toLocalDate().equals(LocalDate.now())).
                mapToDouble(Weather::getTemperature).average().orElseThrow();
    }

    // «Перенесённый из интерфейса»
    /* В следующих модулях будет БД, где мы нормализуем данные, выделив имя региона и его id в отдельную сущность.
       Пока, учитывая, что данный метод должен так же принимать дату, температуру и прочее — очевидно, проще так.
       Выделение данной сущности на этом этапе не обозначалось, более того требовало обоснования при реализации
       (отметил преподаватель в чате), потому оставляю в таком сыром-кривом виде, полагая, что так и задумано… */
    public void addCity(Weather weather) throws ThereIsAlreadySuchWeather {
        boolean isThereSuchWeatherMaybeWithOtherTemperature = MyRepository.list.stream().
                anyMatch(w -> w.getId() == weather.getId() &&
                        w.getNameOfRegion().equals(weather.getNameOfRegion()) &&
                        w.getDateTime().equals(weather.getDateTime()));
        if (isThereSuchWeatherMaybeWithOtherTemperature) {
            throw new ThereIsAlreadySuchWeather("There is already such weather (maybe with other temperature)");
        }
        MyRepository.list.add(weather);
    }

    // «Перенесённый из интерфейса»
    // пока так, после добавления Hibernate логика несколько изменится (будет save, который и сохраняет, и обновляет)
    public void saveOrUpdate(Weather weather) throws ThereIsAlreadySuchWeather {
        if (MyRepository.list.contains(weather)) {
            throw new ThereIsAlreadySuchWeather("There is already such weather");
        }
        Optional<Weather> optional = MyRepository.list.stream().
                filter(w -> w.getId() == weather.getId() &&
                        w.getNameOfRegion().equals(weather.getNameOfRegion()) &&
                        w.getDateTime().equals(weather.getDateTime())).findFirst();
        optional.ifPresent(MyRepository.list::remove);
        MyRepository.list.add(weather);
    }

    public void deleteByCity(int id) throws ThereIsNoCityWithThisId {
        int size = MyRepository.list.size();
        MyRepository.list.removeIf(w -> w.getId() == id);
        if (size == MyRepository.list.size()) {
            throw new ThereIsNoCityWithThisId("There is no city with this id in the database");
        }
    }
}
