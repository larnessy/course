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
public class Location {

    String name;

    String region;

    String country;

    @JsonProperty("lat")
    double latitude;

    @JsonProperty("lon")
    double longitude;

    @JsonProperty("tz_id")
    String timeZoneId;

    @JsonProperty("localtime_epoch")
    long localtimeEpoch;

    String localtime;
}
