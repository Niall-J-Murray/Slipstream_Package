package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.DriverRepository;
import me.niallmurray.slipstream.repositories.LeagueRepository;
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
  DriverService driverService;
  @Autowired
  DriverRepository driverRepository;
  @Autowired
  LeagueService leagueService;
  @Autowired
  LeagueRepository leagueRepository;
  @Autowired
  private UserRepository userRepository;

  public void createTeam(User user) {
    Team team = new Team();
    team.setUser(user);
    team.setTeamId(user.getUserId());

    if (!teamNameExists(user.getTeam().getTeamName())) {
      team.setTeamName(user.getTeam().getTeamName());
      user.setTeam(team);
      user.setEmail(user.getEmail());
    }
    team.setFirstPickNumber(randomPickNumber());
    team.setSecondPickNumber(21 - team.getFirstPickNumber()); //So players get 1&20, 2&19 etc. up to 10&11.
    team.setRanking(team.getFirstPickNumber());
    team.setStartingPoints(0.0);
    team.setLeague(leagueService.findNewestLeague());
    addOneTeamToLeague(team);

//    return teamRepository.save(team);
  }


  public void addOneTeamToLeague(Team team) {
    League league = leagueService.findNewestLeague();
    List<Team> teams = league.getTeams();
    // pick numbers cannot be null,updated to correct ones by teamService.
    // team name unique constraint issue since trying to save same name twice...
//    team.setFirstPickNumber(0);
//    team.setSecondPickNumber(0);
    teams.add(team);
    league.setTeams(teams);
    System.out.println(teams);
    leagueRepository.save(league);
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

//  public List<Team> updateAllTeamsRankings() {
//    List<Team> teams = teamRepository.findAll();
//    for (Team team : teams) {
//      Double totalDriverPoints = team.getDrivers().stream()
//              .mapToDouble(Driver::getPoints).sum();
//      team.setTeamPoints(totalDriverPoints - team.getStartingPoints());
//    }
//    teams.sort(Comparator.comparing(Team::getFirstPickNumber).reversed());
//    teams.sort(Comparator.comparing(Team::getTeamPoints).reversed());
//    for (Team team : teams) {
//      team.setRanking(teams.indexOf(team) + 1);
//    }
//    return teamRepository.saveAll(teams);
//  }

  public List<Team> updateLeagueTeamsRankings(League league) {
//    List<Team> teams = teamRepository.findAll().stream()
//            .filter(t -> t.getLeague().equals(league))
//            .collect(Collectors.toList());
    List<Team> teams = league.getTeams();
    for (Team team : teams) {
      Double totalDriverPoints = team.getDrivers().stream()
              .mapToDouble(Driver::getPoints).sum();
      team.setTeamPoints(totalDriverPoints - team.getStartingPoints());
    }
    // Sort by pick order until all picks made
    if (getCurrentPickNumber(league)<21){
      teams.sort(Comparator.comparing(Team::getFirstPickNumber));
      return teams;
    }
    teams.sort(Comparator.comparing(Team::getFirstPickNumber).reversed());
    teams.sort(Comparator.comparing(Team::getTeamPoints).reversed());
    for (Team team : teams) {
      team.setRanking(teams.indexOf(team) + 1);
    }
    return teamRepository.saveAll(teams);
//    return teams;
  }

  private int randomPickNumber() {
    RandomGenerator random = RandomGenerator.getDefault();
    int pickNumber = random.nextInt(1, 11);
    System.out.println("random1: " + pickNumber);

//    for (Team team : teamRepository.findAll()) {
    for (Team team : leagueService.findNewestLeague().getTeams()) {
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
    List<Driver> teamDrivers = user.getTeam().getDrivers();

    if (teamDrivers.size() < 2) {
      teamDrivers.add(driver);
      driver.getTeams().add(team);
      driver.setTeams(driver.getTeams());
    }
//    Double currentPoints = 0.0;
    Double startingPoints = team.getDrivers().stream()
            .mapToDouble(Driver::getPoints).sum();
    team.setDrivers(teamDrivers);
    team.setStartingPoints(startingPoints);
    team.setUser(user);
    user.setTeam(user.getTeam());
//    driver.setTeams(team);

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


  public int getCurrentPickNumber(League league) {
    List<Driver> undraftedDrivers = driverService.getUndraftedDrivers(league);
    System.out.println("pick no?:" + (21 - undraftedDrivers.size()));
    return 21 - undraftedDrivers.size();
  }

  public boolean timeToPick(League league, Long teamId) {
    int firstPickNumber = teamRepository.findById(teamId).get().getFirstPickNumber();
    int secondPickNumber = teamRepository.findById(teamId).get().getSecondPickNumber();
    return firstPickNumber == getCurrentPickNumber(league) || secondPickNumber == getCurrentPickNumber(league);
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
