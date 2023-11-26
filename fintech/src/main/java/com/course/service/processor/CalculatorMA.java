package com.course.service.processor;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class CalculatorMA {
    public double calculateAverage(LinkedList<Double> temperatures) {
        double sum = 0;

        for (double temperature : temperatures) {
            sum += temperature;
        }

        return sum / temperatures.size();
    }
}
