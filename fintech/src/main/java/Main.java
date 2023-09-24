import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
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

        // first
        System.out.println(list.stream().mapToDouble(Weather::getTemperature).average().orElse(0) + "\n");

        // second
        int temperature = 3;
        Set<String> regions = Functions.temperatureAboveThan(list, temperature);

        System.out.println("Temperature above than " + temperature + " in:\n" +
                "{" + String.join(", ", regions) + "}\n");

        // third (no uniqueness required)
        Map<Integer, List<Integer>> map1 = list.stream()
                .collect(Collectors.groupingBy(Weather::getId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));

        System.out.println(map1 + "\n");

        // fourth
        Map<Integer, List<Weather>> map2 = list.stream()
                .collect(Collectors.groupingBy(Weather::getTemperature, Collectors.toList()));

        System.out.println("{");
        map2.forEach((key, value) -> {
            System.out.println(key + " :");
            value.forEach(weather -> System.out.println("  " + weather));
        });
        System.out.println("}");
    }
}
