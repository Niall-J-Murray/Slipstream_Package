package me.niallmurray.slipstream.web;

import me.niallmurray.slipstream.dto.DriverStanding;
import me.niallmurray.slipstream.dto.DriverStandingResponse;
import me.niallmurray.slipstream.dto.StandingsList;
import me.niallmurray.slipstream.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
public class DriverController {

  @Autowired
  DriverService driverService;

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

  @ResponseBody
  @GetMapping("/driverStandingsJSON")
  public ResponseEntity<DriverStandingResponse> getDriverStandingsResponse() {
//      String timeFrame = "week";
    URI uri = uriBuilder();

    RestTemplate response = new RestTemplate();

    DriverStandingResponse driverStandingResponse = response.getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class).getBody();

//    System.out.println(driverStandingResponse);

//    System.out.println(response.getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class));
    return response.getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class);
  }

  @GetMapping("/driverStandings")
  public String getDriverStandings(ModelMap modelMap){
    DriverStandingResponse driverStandingResponseBody = getDriverStandingsResponse().getBody();
    List<StandingsList> standingsLists = driverStandingResponseBody.mRData.standingsTable.standingsLists;
    List<DriverStanding> currentStandings = standingsLists.listIterator().next().driverStandings;
//    System.out.println("Controller: "+currentStandings);
//    System.out.println("------");
    driverService.addDrivers(currentStandings);
    modelMap.addAttribute("allDrivers",driverService.findAll());
    return "/test";
  }
}
