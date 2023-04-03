package me.niallmurray.slipstream.web;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.dto.DriverStanding;
import me.niallmurray.slipstream.dto.DriverStandingResponse;
import me.niallmurray.slipstream.dto.StandingsList;
import me.niallmurray.slipstream.security.ActiveUserStore;
import me.niallmurray.slipstream.service.AdminService;
import me.niallmurray.slipstream.service.DriverService;
import me.niallmurray.slipstream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {

  @Autowired
  ActiveUserStore activeUserStore;
  @Autowired
  private AdminService adminService;
  @Autowired
  private UserService userService;
  @Autowired
  DriverService driverService;
  @Value("${ergast.urls.base}${ergast.urls.currentDriverStandings}")
  private String f1DataApi;
//  http[s]://ergast.com/api/<series>/<season>/<round>/...
//
//  where:
//  <series> 	should be set to "f1"
//  <season> 	is a 4 digit integer
//  <round> 	is a 1 or 2 digit integer

  @GetMapping("/admin")
  public String getAdmin(ModelMap modelMap) {
    List<User> allUserAccounts = adminService.getAllUserAccounts();
    modelMap.addAttribute("users", allUserAccounts);
    modelMap.addAttribute("activeUsers", activeUserStore.getUsers());
    modelMap.addAttribute("isLoggedIn", true);
    modelMap.addAttribute("isAdmin", true);
    modelMap.addAttribute("allDrivers", driverService.sortDriversStanding());

    return "admin";
  }

  public URI uriBuilder() {

    return UriComponentsBuilder.fromHttpUrl("https://ergast.com/api/f1/")
            .build()
            .toUri();
  }

  @ResponseBody
  @GetMapping("admin/driverStandingsJSON")
  public ResponseEntity<DriverStandingResponse> getDriverStandingsResponse() {
    URI uri = uriBuilder();
//    DriverStandingResponse driverStandingResponse = response.getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class).getBody();
    return new RestTemplate().getForEntity("https://ergast.com/api/f1/current/driverStandings.json", DriverStandingResponse.class);
  }

  @PostMapping("/admin/addDrivers")
  public String getAddDrivers(ModelMap modelMap) {
    List<StandingsList> standingsLists = Objects.requireNonNull(getDriverStandingsResponse().getBody())
            .mRData.standingsTable
            .standingsLists;
    List<DriverStanding> currentStandings = standingsLists.listIterator().next().driverStandings;
    List<Driver> drivers = driverService.mapDTOToDrivers(currentStandings);


    driverService.addDrivers(drivers);
    modelMap.addAttribute("allDrivers", driverService.sortDriversStanding());
    return "redirect:/admin";
  }
  @PostMapping("/admin/updateDrivers")
  public String getUpdateDriverStandings(ModelMap modelMap) {
    List<StandingsList> standingsLists = Objects.requireNonNull(getDriverStandingsResponse().getBody())
            .mRData.standingsTable
            .standingsLists;
    List<DriverStanding> currentStandings = standingsLists.listIterator().next().driverStandings;
    List<Driver> drivers = driverService.mapDTOToDrivers(currentStandings);


    driverService.updateDrivers(drivers);
    modelMap.addAttribute("allDrivers", driverService.sortDriversStanding());
    return "redirect:/admin";
  }


}
//For testing points/standing update
//    drivers.stream()
//            .filter(driver -> driver.getFirstName().equals("Yuki"))
//            .findFirst().ifPresent(testDriver -> {testDriver.setPoints(22.0); testDriver.setStanding(4);});
// Separate add and update driver methods to different functions on admin page.