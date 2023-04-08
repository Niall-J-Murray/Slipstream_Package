package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.repositories.LeagueRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LeagueService {
  @Autowired
  LeagueRepository leagueRepository;

  public League createLeague() {
    League league = new League();
    league.setLeagueName("League # " + (leagueRepository.findAll().size() + 1));
    league.setTeams(new ArrayList<>());
    return leagueRepository.save(league);
  }

  public void updateLeague(League league) {
    leagueRepository.save(league);
  }

  public void addOneTeamToLeague(Long leagueId, Team team) {
    League league = leagueRepository.findById(leagueId).get();
    List<Team> teams = league.getTeams();
    // pick numbers cannot be null,updated to correct ones by teamService.
    // team name unique constraint issue since tring to save same name twice...
    team.setFirstPickNumber(1);
    team.setSecondPickNumber(20);
    teams.add(team);
    leagueRepository.save(league);
  }

//  public void addTeamsToLeague(Long leagueId) {
//    League league = leagueRepository.findById(leagueId).get();
//    List<Team> teams = teamRepository.findAll();
//    for (Team team : teams) {
//      if (Objects.equals(team.getLeague().getLeagueId(), leagueId)) {
//        league.getTeams().add(team);
////        leagueRepository.updateOneTeamInLeague(league.getLeagueId(), team);
//      }
//    }
//    league.setTeams(league.getTeams());
//    List<Team> allTeams = league.getTeams();
//    teams.add(team);
//    league.setTeams(teams);
//    System.out.println("save: " + league);
//    leagueRepository.save(league);
//    leagueRepository.updateTeamsInLeague(league.getLeagueId(), allTeams);


  public List<Team> getAllTeamsInLeague(Long leagueId) {
    return leagueRepository.findById(leagueId).get().getTeams();
  }

  public League findNewestLeague() {
    List<League> allLeagues = leagueRepository.findAll();
    if (allLeagues.size() < 1) {
      return createLeague();
    }
    // If leagues exist, get the latest created (last in list)
    return allLeagues.get(allLeagues.size() - 1);
  }

  public List<League> findAll() {
    return leagueRepository.findAll();
  }

  public League findLeagueById(Long leagueId) {
  return leagueRepository.findById(leagueId).get();
  }


}
