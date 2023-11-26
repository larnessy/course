package com.course.listener;

import com.course.model.entity.WeatherEntity;
import com.course.service.processor.CalculatorMA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Component
@Slf4j
public class ListenerGettingWeather {

    private final CalculatorMA calculatorMA;

    // стартовые запрос к БД делать не стал, полагая, что данные должны быть актуальными…
    private final Map<String, LinkedList<Double>> cityTemperatureData;

    @Autowired
    public ListenerGettingWeather(CalculatorMA calculatorMA, @Qualifier("cityNames") String[] cityNames) {
        this.calculatorMA = calculatorMA;
        cityTemperatureData = new HashMap<>(32, 1);

        for (String name : cityNames) {
            cityTemperatureData.put(name, new LinkedList<>());
        }
    }

    // можно использовать более эффективный алгоритм, но зачем, когда речь о for для 30 double?
    @KafkaListener(topics = "demo_topic", groupId = "myGroup")
    public void calculate30MovingAverageForCity(WeatherEntity weatherEntity) {
        try {
            String cityName = weatherEntity.getCity().getName();
            LinkedList<Double> temperatures = cityTemperatureData.get(cityName);
            temperatures.add(weatherEntity.getTemperature());

            if (temperatures.size() > 30) {
                temperatures.removeFirst();
            } else if (temperatures.size() < 30) {
                log.info("Недостаточно данных для вычисления 30 Moving Average температуры в городе: " + cityName);
                return;
            }

            if (temperatures.size() == 30) {
                double average = calculatorMA.calculateAverage(temperatures);
                log.info("30 Moving average температуры в городе: " + cityName + " = " + average);
            }
        } catch (Exception exception) {
            log.error("Во время выполнения calculate30MovingAverageForCity было выброшено исключение");
        }
    }
}
