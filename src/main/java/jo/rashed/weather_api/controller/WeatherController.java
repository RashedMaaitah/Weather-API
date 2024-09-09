package jo.rashed.weather_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jo.rashed.weather_api.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/")
public class WeatherController {


    @Value("${VISUAL_CROSSING_API_KEY}")
    private String API_KEY;


    @GetMapping("{country}")
    @Cacheable(value = "weather_single", key = "#country")
    public Weather getWeather(@PathVariable String country) throws JsonProcessingException {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + country + "/?key=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(data);

        var days = rootNode.path("days");
        var today = days.get(0);

        String address = rootNode.get("address").asText();
        String todayDate = today.get("datetime").asText();
        String todayDescription = today.get("description").asText();
        double temperature = fahrenheitToCelsius(today.get("temp").asDouble());

        var todayWeather = new Weather(todayDate, todayDescription, address, temperature);

        return todayWeather;

    }


    @GetMapping("{country}/all")
    @Cacheable(value = "weather", key = "#country")
    public List<Weather> getAllWeather(@PathVariable String country) throws JsonProcessingException {
        String url = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + country + "/?key=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        String data = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(data);


        List<Weather> result = new ArrayList<>();

        var days = rootNode.path("days");
        for (JsonNode day : days) {

            String address = rootNode.get("address").asText();
            String todayDate = day.get("datetime").asText();
            String dayDescription = day.get("description").asText();
            double temperature = fahrenheitToCelsius(day.get("temp").asDouble());

            var weather = new Weather(todayDate, dayDescription, address, temperature);
            result.add(weather);
        }

        return result;

    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) / 1.8;
    }
}
