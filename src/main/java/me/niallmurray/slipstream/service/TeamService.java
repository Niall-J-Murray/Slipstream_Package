package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.DriverRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import me.niallmurray.slipstream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {
  @Autowired
  UserService userService;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  DriverRepository driverRepository;

  // Set list for up to 10 players for now. Can be changed or made dynamic according to number of players per league.
  private List<Integer> pickNumbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
  // Temp fields for testing.
  private List<Team> teamsInLeague = new ArrayList<>(20);
  private List<User> usersForNextLeague = new ArrayList<>(20);
  private int teamCounter = 0;
  @Autowired
  private UserRepository userRepository;

  public Team createTeam(User user) {
//    try {
//      f1DriverRepository.addDriversManually();
//    } catch (FileNotFoundException e) {
//      throw new RuntimeException(e);
//    }

    if (getAllTeams().size() < 11) {
      Team team = new Team();
      team.setUser(user);
      team.setUserId(user.getUserId());
      team.setFirstPickNumber(randomPickNumber());
      team.setSecondPickNumber(21 - team.getFirstPickNumber()); //So players get 1&20, 2&19 etc. up to 10&11.
      if (!teamNameExists(user.getTeam().getTeamName())) {
        team.setTeamName(user.getTeam().getTeamName());
        user.setTeam(team);
        user.setEmail(user.getEmail());
      }
      // Fix issue where new teams cannot be saved even after existing teams have been deleted from DB.
      // e.g: "Error: Duplicate entry '1' for key 'team.UK_bkasmvd9arje65etjtxd5tf39'"
      // Possible issue with cascade settings?
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
    usersForNextLeague.add(user);
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
    List<Team> allTeams = teamRepository.findAll();
    for (Team team : allTeams) {
      if (Objects.equals(team.getTeamName(), teamName))
        return true;
    }
    return false;
  }

  public Long addDriverToTeam(Long userId, Long driverId){
    User user = userService.findById(userId);
    Driver driver = driverRepository.findById(driverId).get();
    // teamId == userId
    Team team = teamRepository.findById(userId).get();
    Set<Driver> userDrivers = user.getTeam().getDrivers();
    if (userDrivers.size() < 2){
      userDrivers.add(driver);
      driver.setTeam(team);
    }
    team.setDrivers(userDrivers);
    team.setUser(user);

    user.setTeam(user.getTeam());

    System.out.println("Add Driver-----");
    System.out.println(user.getTeam());
    System.out.println(user.getTeam().getDrivers());
    System.out.println(user);
    System.out.println(team);
    userRepository.save(user);
    teamRepository.save(team);
//    driverRepository.save(driver);


    return driverId;
  }

  public List<Team> getAllTeams() {
    return teamRepository.findAll();
  }
}
