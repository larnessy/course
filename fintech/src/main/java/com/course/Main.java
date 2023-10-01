import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}


/*

        List<Weather> list = Arrays.asList(
                new Weather(1, "region1", 1, LocalDateTime.now()),
                new Weather(2, "region2", 2, LocalDateTime.now()),
                new Weather(3, "region3", 3, LocalDateTime.now()),
                new Weather(4, "region4", 4, LocalDateTime.now()),
                new Weather(5, "region5", 5, LocalDateTime.now()),
                new Weather(5, "region5", 6,
                        LocalDateTime.of(2023, 9, 24, 1, 0, 0)),
                new Weather(5, "region5", 7,
                        LocalDateTime.of(2023, 9, 24, 2, 0, 0)),
                new Weather(5, "region5", 7,
                        LocalDateTime.of(2023, 9, 24, 3, 0, 0)));

        // first (сделал бы по id, но так как пока привязки имени к id нет…)
        Map<String, Double> averageTemperatureByRegion = WeatherProcessor.mapAverageTemperatureByRegion(list);
        System.out.println("Average temperatures by region:\n" + averageTemperatureByRegion + "\n");

        // second
        int temperature = 3;
        Set<String> regions = WeatherProcessor.findRegionsWithTemperatureAboveThan(list, temperature);
        System.out.println("Temperature above than " + temperature + " in:\n" +
                "{" + String.join(", ", regions) + "}\n");

        // third (no uniqueness required)
        Map<Integer, List<Double>> temperaturesById = WeatherProcessor.mapTemperaturesById(list);
        System.out.println("Temperatures by id:\n" + temperaturesById + "\n");

        // fourth
        Map<Double, List<Weather>> weatherByTemperature = WeatherProcessor.mapWeatherByTemperature(list);
        System.out.println("Weather by temperature:\n" + "{");
        weatherByTemperature.forEach((key, value) -> {
            System.out.println(key + " :");
            value.forEach(weather -> System.out.println("  " + weather));
        });
        System.out.println("}");

 */