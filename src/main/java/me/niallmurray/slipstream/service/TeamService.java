package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.DriverRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import me.niallmurray.slipstream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

@Service
public class TeamService {
  @Autowired
  UserService userService;
  @Autowired
  TeamRepository teamRepository;
  @Autowired
  DriverRepository driverRepository;
  @Autowired
  LeagueService leagueService;
  // Temp fields for testing.
  private List<Team> teamsInLeague = new ArrayList<>(20);
  private List<User> usersForNextLeague = new ArrayList<>(20);
  private int teamCounter = 0;
  @Autowired
  private UserRepository userRepository;

  public Team createTeam(User user) {
//    if (getAllTeams().size() < 11) {
    Team team = new Team();
    team.setUser(user);
    team.setTeamId(user.getUserId());
    team.setFirstPickNumber(randomPickNumber());
    team.setSecondPickNumber(21 - team.getFirstPickNumber()); //So players get 1&20, 2&19 etc. up to 10&11.
//    team.setLeague(leagueService.findNewestLeague());
    team.setTeamPoints(0.0);
    team.setRanking(1);
//    League currentLeague = leagueService.findNewestLeague();
//    currentLeague.getTeams().add(team);
//    currentLeague.setTeams(currentLeague.getTeams());

//    leagueService.addTeamToLeague(currentLeague.getLeagueId(), newTeam);
    if (!teamNameExists(user.getTeam().getTeamName())) {
      team.setTeamName(user.getTeam().getTeamName());
      user.setTeam(team);
      user.setEmail(user.getEmail());
    }
//    leagueService.updateLeague(currentLeague);
    // Fix issue where new teams cannot be saved even after existing teams have been deleted from DB.
    // e.g: "Error: Duplicate entry '1' for key 'team.UK_bkasmvd9arje65etjtxd5tf39'"
    // Issue occurring intermittently when saving teams without having removed any.
    // Seems to be related to chosen team name?
    // "Duplicate entry '10' for key 'team.UK_bkasmvd9arje65etjtxd5tf39'"
    // Possible issue with cascade settings?

//    leagueService.addTeamToLeague(currentLeague, team);
//    leagueService.updateLeague(currentLeague);
//    leagueService.updateLeague(currentLeague);
    return teamRepository.save(team);
  }
// for testing, new users should not be able to create team if league is full.
// will add feature to create new league when current one is full.
//    Team team = new Team();
//    team.setUser(user);
//    team.setTeamId(user.getUserId());
//    team.setFirstPickNumber(0);
//    team.setSecondPickNumber(0);
//    team.setTeamName(user.getTeam().getTeamName());
//    user.setTeam(team);
//    user.setEmail(user.getEmail());
//    usersForNextLeague.add(user);
//    teamRepository.save(team);
//  }

  public void updateAllTeamsRankings() {
    List<Team> teams = teamRepository.findAll();
    for (Team team : teams) {
//      Double totalDriverPoints = team.getDrivers().iterator().next().getPoints();
      Double totalDriverPoints = team.getDrivers().stream()
              .mapToDouble(Driver::getPoints).sum();
      team.setTeamPoints(totalDriverPoints);

//      teamRepository.save(team);
    }
    teams.sort(Comparator.comparing(Team::getFirstPickNumber).reversed());
    teams.sort(Comparator.comparing(Team::getTeamPoints).reversed());
    for (Team team : teams) {
      team.setRanking(teams.indexOf(team) + 1);
    }
    teamRepository.saveAll(teams);
  }

  private int randomPickNumber() {
    RandomGenerator random = RandomGenerator.getDefault();
    int pickNumber = random.nextInt(1, 11);
    System.out.println("random1: " + pickNumber);

    for (Team team : teamRepository.findAll()) {
      if (team.getFirstPickNumber() == pickNumber) {
        pickNumber = randomPickNumber();
      }
    }
    System.out.println("random2: " + pickNumber);
    return pickNumber;
  }

  public boolean teamNameExists(String teamName) {
    List<Team> allTeams = teamRepository.findAll();
    for (Team team : allTeams) {
      if (Objects.equals(team.getTeamName(), teamName))
        return true;
    }
    return false;
  }

  public Long addDriverToTeam(Long userId, Long driverId) {
    User user = userService.findById(userId);
    Driver driver = driverRepository.findById(driverId).get();
    Team team = teamRepository.findById(user.getTeam().getTeamId()).get();
    List<Driver> userDrivers = user.getTeam().getDrivers();
    if (userDrivers.size() < 2) {
      userDrivers.add(driver);
      driver.setTeam(team);
    }
    team.setDrivers(userDrivers);
    team.setUser(user);
    user.setTeam(user.getTeam());
    driver.setTeam(team);

    System.out.println("Add Driver-----");
    System.out.println(user.getTeam());
    System.out.println(user.getTeam().getDrivers());
    System.out.println(user);
    System.out.println(team);

    userRepository.save(user);
    teamRepository.save(team);
    driverRepository.save(driver);
    return driverId;
  }

  public int getCurrentPickNumber() {
    List<Driver> undraftedDrivers = driverRepository.findAllByOrderByStandingAsc();
    undraftedDrivers.removeIf(driver -> driver.getTeam() != null);
    return 21 - undraftedDrivers.size();
  }

  public boolean timeToPick(Long teamId) {
    int firstPickNumber = teamRepository.findById(teamId).get().getFirstPickNumber();
    int secondPickNumber = teamRepository.findById(teamId).get().getSecondPickNumber();
    return firstPickNumber == getCurrentPickNumber() || secondPickNumber == getCurrentPickNumber();
  }

  public List<Team> getAllTeams() {
    return teamRepository.findAll();
  }

  public List<Team> getAllTeamsByNextPick() {
    List<Team> allTeams = teamRepository.findAll();
    allTeams.sort(Comparator.comparing(Team::getFirstPickNumber));
//    System.out.println(allTeams);
//    allTeams.sort(Comparator.comparing(Team::getSecondPickNumber));
//    System.out.println(allTeams);
    return allTeams;
  }

  public List<Team> getAllTeamsByRanking() {
    List<Team> allTeams = teamRepository.findAll();
    allTeams.sort(Comparator.comparing(Team::getRanking));

    return allTeams;
  }
}
