package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.F1DriverRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
@Service
public class TeamService {
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  F1DriverRepository f1DriverRepository;

  public void createTeam(User user) {
    try {
      f1DriverRepository.addDriversManually();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    Team team = new Team();
    user.setTeam(team);
    team.setTeamName(user.getTeam().getTeamName());
    team.setPickNumber((int) ((Math.random()*(10-1)) + 1));
    team.setUser(user);

    System.out.println(team);
    teamRepository.save(team);
  }

  public void createTeam(User user,Team team) {
    try {
      f1DriverRepository.addDriversManually();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    user.setTeam(team);
    team.setTeamName(user.getTeam().getTeamName());
    team.setPickNumber((int) ((Math.random()*(10-1)) + 1));
    team.setUser(user);

    System.out.println(team);
    teamRepository.save(team);
  }

  public boolean teamNameExists(String teamName) {
    return true;
  }
}
