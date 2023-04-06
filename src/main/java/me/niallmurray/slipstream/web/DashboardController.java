package me.niallmurray.slipstream.web;


import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.service.DriverService;
import me.niallmurray.slipstream.service.LeagueService;
import me.niallmurray.slipstream.service.TeamService;
import me.niallmurray.slipstream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController {

  @Autowired
  private UserService userService;
  @Autowired
  private TeamService teamService;
  @Autowired
  private DriverService driverService;
  @Autowired
  private LeagueService leagueService;

  @GetMapping("/dashboard")
  public String redirectUserDashboard(@AuthenticationPrincipal User userAuth) {
    return "redirect:/dashboard/" + userAuth.getUserId();
  }

  @GetMapping("/dashboard/{userId}")
  public String getDashboard(@AuthenticationPrincipal User userAuth, ModelMap modelMap, @PathVariable Long userId) {
    User user = userService.findById(userId);
    List<Team> allTeams = teamService.getAllTeams();

    League league = null;
    if (allTeams.size() == 0 || allTeams.size() % 10 == 0) {
//    if (allTeams.size() == 0) {
      league = leagueService.createLeague();
//      league.setTeams(allTeams);
    }
    modelMap.addAttribute("user", user);
    modelMap.addAttribute("driver", new Driver());
    modelMap.addAttribute("teams", allTeams);
    modelMap.addAttribute("league",leagueService.findNewestLeague());
    modelMap.addAttribute("teamsByPick", teamService.getAllTeamsByNextPick());
    modelMap.addAttribute("allDrivers", driverService.sortDriversStanding());
    modelMap.addAttribute("availableDrivers", driverService.getUndraftedDrivers());
    modelMap.addAttribute("currentPickNumber", teamService.getPickNumber());
    modelMap.addAttribute("gameFull", false);
    modelMap.addAttribute("timeToPick", false);

    if(league!=null){

      List<Team> allTeamsInLeague = leagueService.getAllTeamsInLeague(league.getLeagueId());
      modelMap.addAttribute("teamsInLeague",allTeamsInLeague);
    }

    if (userService.isLoggedIn(user)) {
      modelMap.addAttribute("isLoggedIn", true);
    }
    if (userService.isAdmin(user)) {
      modelMap.addAttribute("isAdmin", true);
    }
// Currently ony one league up to 10 players(not including admin) available.
// After game is full, new users can register, but cannot create new team.
    if (allTeams.size() >= 10) {
      modelMap.addAttribute("gameFull", true);
    }

    if (!userService.isAdmin(user) && user.getTeam() != null) {
      if (teamService.timeToPick(user.getTeam().getTeamId())) {
        modelMap.addAttribute("timeToPick", true);
      }
    }

    teamService.updateAllTeamsRankings();
    modelMap.addAttribute("teamsByRank", teamService.getAllTeamsByRanking());
    return "dashboard";
  }

  // Consider JS for team name as only one string needs to be parsed from client.
  @PostMapping("/dashboard/{userId}")
  public String postCreateTeam(@AuthenticationPrincipal User userAuth, @PathVariable Long userId, User user) {
    // Check for unique team names.
    String teamName = user.getTeam().getTeamName();
    if (!teamService.teamNameExists(teamName)) {
      user = userService.findById(userId);
      if (user.getTeam() == null) {
        user.setTeam(new Team());
      }
      user.getTeam().setTeamName(teamName);
      teamService.createTeam(user);
//      leagueService.addTeamToLeague(league.getLeagueId(),user.getTeam());
//      int currentLeagueId = leagueService.findAll().size();
      League league = leagueService.findNewestLeague();
      leagueService.addTeamToLeague(league.getLeagueId(),user.getTeam());
      return "redirect:/dashboard/" + userId;
    }
    return "redirect:/dashboard/" + userId + "?error";
  }

  @PostMapping("/dashboard/{userId}/draftPick")
  public String postMakePick(@PathVariable Long userId, Driver driver, User user) {
    System.out.println(driver);
    Long driverId = driver.getDriverId();
    teamService.addDriverToTeam(userId, driverId);
    return "redirect:/dashboard/" + userId;
  }

}


