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
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DashboardController {

  @Autowired
  private UserService userService;
  @Autowired
  private TeamService teamService;

  @GetMapping("/dashboard")
  public String getDashboard(@AuthenticationPrincipal User user, ModelMap model) {
    model.addAttribute("user", user);
    if (userService.isLoggedIn(user)) {
      model.addAttribute("isLoggedIn", true);
    }
    return "dashboard";
  }

  @PostMapping("/dashboard")
  public String postCreateTeam(@AuthenticationPrincipal User user, ModelMap model, String teamName)  {
    Team team = new Team();
    team.setTeamName(teamName);
    team.setUser(user);
    user.setTeam(team);

//    if (teamService.teamNameExists(user.getTeam().getTeamName())) {
//      model.addAttribute("teamExists", "Team name taken");
//      return "/dashboard";
//    }
//    teamService.createTeam(user.getTeam().getTeamName());

//    user.getTeam().setTeamName(teamName);
    System.out.println(user);
    System.out.println(user.getTeam().getTeamName());
//    teamService.createTeam(user);
    teamService.createTeam(user,team);
    return "redirect:/dashboard";
  }
}

// Team name and/or team not being passed to controller or service?
// Review previous assignments to check team/account creation.
// Consider JS as only one string needs to be parsed from client.
