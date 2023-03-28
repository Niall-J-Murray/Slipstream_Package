package me.niallmurray.slipstream.web;

import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.security.ActiveUserStore;
import me.niallmurray.slipstream.service.AdminService;
import me.niallmurray.slipstream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

  @Autowired
  ActiveUserStore activeUserStore;
  @Autowired
  private AdminService adminService;
  @Autowired
  private UserService userService;

  @GetMapping("/admin")
  public String getAdmin(ModelMap model) {
    List<User> allUserAccounts = adminService.getAllUserAccounts();
    model.addAttribute("users", allUserAccounts);
    model.addAttribute("activeUsers", activeUserStore.getUsers());
    model.addAttribute("isLoggedIn", true);
    model.addAttribute("isAdmin", true);

    return "admin";
  }

}
