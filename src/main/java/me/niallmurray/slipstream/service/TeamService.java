package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.F1DriverRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TeamService {
  @Autowired
  UserService userService;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  F1DriverRepository f1DriverRepository;

  // Set list for up to 10 players for now. Can be changed or made dynamic according to number of players per league.
  private List<Integer> pickNumbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  private int teamCounter = 0;

  public Team createTeam(User user) {
//    try {
//      f1DriverRepository.addDriversManually();
//    } catch (FileNotFoundException e) {
//      throw new RuntimeException(e);
//    }

    if (teamCounter < 10) {
      Team team = new Team();
      team.setUser(user);
      team.setUserId(user.getUserId());
      team.setFirstPickNumber(randomPickNumber());
      team.setSecondPickNumber(21 - team.getFirstPickNumber()); //So players get 1&20, 2&19 etc. up to 10&11.
      team.setTeamName(user.getTeam().getTeamName());

      user.setTeam(team);
      user.setEmail(user.getEmail());
      teamCounter++;
      return teamRepository.save(team);
    }
// for testing, new users should not be able to create team if league is full.
// will add feature to create new league when current one is full.
    Team team = new Team();
    team.setUser(user);
    team.setUserId(user.getUserId());
    team.setFirstPickNumber(0);
    team.setSecondPickNumber(0); //So players get 1&20, 2&19 etc. up to 10&11.
    team.setTeamName(user.getTeam().getTeamName());
    user.setTeam(team);
    user.setEmail(user.getEmail());
    return teamRepository.save(team);
  }

  private int randomPickNumber() {
    // Shuffle pickNumbers list, then randomly remove 1 element and return as random pick.
    Collections.shuffle(pickNumbers);
    if (pickNumbers.size() == 0) {
      return 0;
    }
    return pickNumbers.remove(new Random().nextInt(pickNumbers.size()));
  }

  public boolean teamNameExists(String teamName) {
    return true;
  }
}
