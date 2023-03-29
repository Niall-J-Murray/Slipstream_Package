package me.niallmurray.slipstream.web;


import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
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

  @GetMapping("/dashboard")
  public String redirectUserDashboard(@AuthenticationPrincipal User userAuth) {
    return "redirect:/dashboard/" + userAuth.getUserId();
  }

  @GetMapping("/dashboard/{userId}")
  public String getDashboard(@AuthenticationPrincipal User userAuth, ModelMap model, @PathVariable Long userId) {
    User user = userService.findById(userId);
    List<Team> allTeams = teamService.getAllTeams();
    model.addAttribute("user", user);
    model.addAttribute("teams", allTeams);
    if (userService.isLoggedIn(user)) {
      model.addAttribute("isLoggedIn", true);
    }
    if (userService.isAdmin(user)) {
      model.addAttribute("isAdmin", true);
    }
// Currently ony one league up to 10 players(not including admin) available.
// After game is full, new users can register, but cannot create new team.
    if (allTeams.size() >= 10) {
      model.addAttribute("gameFull", true);
    }
    return "dashboard";
  }

  // Consider JS for team name as only one string needs to be parsed from client.
  @PostMapping("/dashboard/{userId}")
  public String postCreateTeam(@AuthenticationPrincipal User userAuth,@PathVariable Long userId, User user, ModelMap model) {
    // Check for unique team names.
    String teamName = user.getTeam().getTeamName();
    if (!teamService.teamNameExists(teamName)) {
      user = userService.findById(userId);
      if (user.getTeam() == null) {
        user.setTeam(new Team());
      }
      user.getTeam().setTeamName(teamName);
      teamService.createTeam(user);
      return "redirect:/dashboard/" + userId;
    }
    return "redirect:/dashboard/"+userId+"?error";
  }


}


