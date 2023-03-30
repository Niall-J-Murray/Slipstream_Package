package me.niallmurray.slipstream.web;

import me.niallmurray.slipstream.dto.DriverStandingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class DriverController {


//  @Value("https://ergast.com/api/f1/current/driverStandings")
  @Value("${ergast.urls.base}${ergast.urls.currentDriverStandings}")
  private String f1DataApi;

//  http[s]://ergast.com/api/<series>/<season>/<round>/...
//
//  where:
//  <series> 	should be set to "f1"
//  <season> 	is a 4 digit integer
//  <round> 	is a 1 or 2 digit integer

  public URI uriBuilder() {

    return UriComponentsBuilder.fromHttpUrl("https://ergast.com/api/f1/")
            .build()
            .toUri();
  }

  @GetMapping("/driverStandings")
  public ResponseEntity<DriverStandingResponse> getDriverStandings() {
//      String timeFrame = "week";
    URI uri = uriBuilder();

    System.out.println(new RestTemplate().getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class));
    return new RestTemplate().getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class);
  }
}
