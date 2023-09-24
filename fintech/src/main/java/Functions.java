import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Functions {
    public static Set<String> temperatureAboveThan(List<Weather> list, int temperature) {
        return list.stream()
                .filter(w -> w.getTemperature() > temperature)
                .map(Weather::getNameOfRegion)
                .collect(Collectors.toSet());
    }
}
