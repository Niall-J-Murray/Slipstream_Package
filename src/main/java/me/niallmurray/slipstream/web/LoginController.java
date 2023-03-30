package me.niallmurray.slipstream.web;

import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.security.ActiveUserStore;
import me.niallmurray.slipstream.service.AdminService;
import me.niallmurray.slipstream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

  @Autowired
  ActiveUserStore activeUserStore;
  @Autowired
  private AdminService adminService;
  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String rootRedirect() {
    return "redirect:/home";
  }

  @GetMapping("/home")
  public String getHome(@AuthenticationPrincipal User user, ModelMap model) {
    model.addAttribute("activeUsers", activeUserStore.getUsers());
    model.addAttribute("users", adminService.getAllUserAccounts());
    model.addAttribute("user", user);

    if (userService.isLoggedIn(user)) {
      model.addAttribute("isLoggedIn", true);
    }

    return "home";
  }

  // Consider moving to different controller, perhaps with home?
  @GetMapping("/current_standings")
  public String getCurrentStandings(){
    return "current_standings";
  }

  @GetMapping("/login")
  public String getLogin(@AuthenticationPrincipal User user, ModelMap model) {
    List<String> activeUsers = activeUserStore.getUsers();
    if (user != null && activeUsers.contains(user.getUsername())) {
      return "redirect:/home";
    }
    model.addAttribute("activeUsers", activeUsers);
    return "login";
  }

  @GetMapping("/confirm_logout")
  public String getConfirmLogout(@AuthenticationPrincipal User user, ModelMap model) {
    model.addAttribute("user", user);
    return "confirm_logout";
  }

  @GetMapping("/logout")
  public String getLogout() {
    return "login";
  }
}
