package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.F1DriverRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
  @Autowired
  UserService userService;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  F1DriverRepository f1DriverRepository;

  public Team createTeam(User user) {
//    try {
//      f1DriverRepository.addDriversManually();
//    } catch (FileNotFoundException e) {
//      throw new RuntimeException(e);
//    }
//    Team team = new Team();
//    user.setTeam(team);
//    team.setTeamName(user.getTeam().getTeamName());
//
//    team.setUser(user);
//    Team team = user.getTeam();
    Team team = new Team();
    team.setUser(user);
    team.setUserId(user.getUserId());
    team.setPickNumber((int) ((Math.random()*(10-1)) + 1));
    team.setTeamName(user.getTeam().getTeamName());

    user.setTeam(team);
    user.setEmail(user.getEmail());
//    userService.updateUserTeam(user.getUserId(),team);
//    user = userService.findById(user.getUserId());
    System.out.println(team);

    System.out.println(team);


    return teamRepository.save(team);
  }

  public void createTeam(Team team) {
    team.setPickNumber((int) ((Math.random()*(10-1)) + 1));

    System.out.println(team);
    teamRepository.save(team);
    //: not-null property references a null or transient value : me.niallmurray.slipstream.domain.Team.teamName
  }

  public Team createTeam(User user, Team team) {
//    try {
//      f1DriverRepository.addDriversManually();
//    } catch (FileNotFoundException e) {
//      throw new RuntimeException(e);
//    }

//    user.setTeam(team);
//    team.setTeamName(user.getTeam().getTeamName())
//    team.setUser(user);

    team.setUserId(user.getUserId());
    team.setUser(user);
    team.setPickNumber((int) ((Math.random()*(10-1)) + 1));
//    team.setTeamName(user.getTeam().getTeamName());
    System.out.println(team);
    return teamRepository.save(team);
  }

  public boolean teamNameExists(String teamName) {
    return true;
  }
}
