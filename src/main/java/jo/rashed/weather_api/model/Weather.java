package jo.rashed.weather_api.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class Weather implements Serializable {
    private String dateTime;
    private String description;
    private String address;
    private double temperature;
}
