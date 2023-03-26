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
//  public String getDashboard(@AuthenticationPrincipal User user, ModelMap model, @PathVariable String userId) {
  public String getDashboard(@AuthenticationPrincipal User userAuth, ModelMap model, @PathVariable Long userId) {
    User user = userService.findById(userId);
//   model.addAttribute("team", user.getTeam());
    model.addAttribute("user", user);
    if (userService.isLoggedIn(user)) {
      model.addAttribute("isLoggedIn", true);
    }
// Currently ony one league up to 10 players available.
// New users can register, but cannot create new team.
// userId 666 = admin userId.
    if (userId > 10 && userId != 666) {
      model.addAttribute("gameFull", true);
    }
    return "dashboard";
  }

  // Consider JS for team name as only one string needs to be parsed from client.
  @PostMapping("/dashboard/{userId}")
  public String postCreateTeam(@PathVariable Long userId, User user) {
    String teamName = user.getTeam().getTeamName();
    user = userService.findById(userId);
    if (user.getTeam() == null) {
      user.setTeam(new Team());
    }
    user.getTeam().setTeamName(teamName);
    teamService.createTeam(user);
    return "redirect:/dashboard/" + userId;
  }

//  @PostMapping("/dashboard/{userId}")
//  public String postCreateTeam(@AuthenticationPrincipal User userAuth,@PathVariable Long userId, ModelMap model)  {
////  public String postCreateTeam(@AuthenticationPrincipal User user, ModelMap model, String teamName)  {
////    User user = userService.findById(userId);
////    Team team = new Team();
////    user.setTeam(team);
////    team.setUser(user);
////    team.setTeamName(user.getTeam().getTeamName());
//
////    if (teamService.teamNameExists(user.getTeam().getTeamName())) {
////      model.addAttribute("teamExists", "Team name taken");
////      return "/dashboard";
////    }
////    teamService.createTeam(user.getTeam().getTeamName());
//
////    user.getTeam().setTeamName(teamName);
////    System.out.println(user);
////    System.out.println(user.getTeam().getTeamName());
//
//    User user = userService.findById(userId);
//
//    Team team = new Team();
//    user.setTeam(team);
//    System.out.println(user.getTeam().getTeamName());
//    team.setUser(user);
//    team.setTeamName(user.getTeam().getTeamName());
////    teamService.createTeam(user);
////    teamService.createTeam(team);
//    teamService.createTeam(user,team);
//    return "redirect:/dashboard/"+userId;
//  }


}


