package com.course.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {
    @JsonProperty("last_updated_epoch")
    long lastUpdatedEpoch;

    @JsonProperty("last_updated")
    String lastUpdated;

    @JsonProperty("temp_c")
    double temperatureCelsius;

    @JsonProperty("temp_f")
    double temperatureFahrenheit;

    Condition condition;
}
