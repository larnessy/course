package com.course.service.crud;

import com.course.exception.myException.ThereIsAlreadySuchWeather;
import com.course.exception.myException.ThereIsNoCityWithThisId;
import com.course.model.Weather;
import com.course.repositorie.MyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CRUDWeatherImpl implements CRUDWeather {

    @Override
    public double findTemperatureInCityToday(int id) {
        return MyRepository.list.stream().filter(w -> w.getId() == id &&
                        w.getDateTime().toLocalDate().equals(LocalDate.now())).
                mapToDouble(Weather::getTemperature).average().orElseThrow();
    }

    @Override
    public void addCity(Weather weather) throws ThereIsAlreadySuchWeather {
        if (MyRepository.list.stream().anyMatch(w -> w.getId() == weather.getId() &&
                w.getNameOfRegion().equals(weather.getNameOfRegion()) &&
                w.getDateTime().equals(weather.getDateTime())))
            throw new ThereIsAlreadySuchWeather("There is already such weather (maybe with other temperature)");
        MyRepository.list.add(weather);
    }

    @Override
    public void saveOrUpdate(Weather weather) throws ThereIsAlreadySuchWeather {
        if (MyRepository.list.contains(weather))
            throw new ThereIsAlreadySuchWeather("There is already such weather");
        Optional<Weather> optional = MyRepository.list.stream().
                filter(w -> w.getId() == weather.getId() &&
                        w.getNameOfRegion().equals(weather.getNameOfRegion()) &&
                        w.getDateTime().equals(weather.getDateTime())).
                findFirst();
        optional.ifPresent(MyRepository.list::remove);
        MyRepository.list.add(weather);
    }

    @Override
    public void deleteByCity(int id) throws ThereIsNoCityWithThisId {
        int size = MyRepository.list.size();
        MyRepository.list.removeIf(w -> w.getId() == id);
        if (size == MyRepository.list.size())
            throw new ThereIsNoCityWithThisId("There is no city with this id in the database");
    }
}
