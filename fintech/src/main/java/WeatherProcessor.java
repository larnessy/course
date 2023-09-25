import java.util.*;
import java.util.stream.Collectors;

public class WeatherProcessor {
    public static Map<String, Double> mapAverageTemperatureByRegion(List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getNameOfRegion,
                        Collectors.averagingDouble(Weather::getTemperature)));
    }

    public static Set<String> findRegionsWithTemperatureAboveThan(List<Weather> list, double temperature) {
        return list.stream()
                .filter(w -> w.getTemperature() > temperature)
                .map(Weather::getNameOfRegion)
                .collect(Collectors.toSet());
    }

    public static Map<Integer, List<Double>> mapTemperaturesById(List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public static Map<Double, List<Weather>> mapWeatherByTemperature (List<Weather> list) {
        return list.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature, Collectors.toList()));
    }
}
