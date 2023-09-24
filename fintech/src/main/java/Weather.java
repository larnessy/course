import lombok.*;
import java.time.LocalDateTime;

@Value
public class Weather {
    int id;
    String nameOfRegion;
    int temperature;
    LocalDateTime dateTime;
}
