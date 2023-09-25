import lombok.*;
import java.time.LocalDateTime;

@Value
public class Weather {
    int id;
    String nameOfRegion;
    double temperature;
    LocalDateTime dateTime;
}
