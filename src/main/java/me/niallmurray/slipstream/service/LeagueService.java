package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.repositories.LeagueRepository;
import me.niallmurray.slipstream.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {
  @Autowired
  LeagueRepository leagueRepository;
  @Autowired
  TeamRepository teamRepository;

  public League createLeague(){
    League league = new League();
    league.setLeagueName("League # " + (leagueRepository.findAll().size() + 1));
    return leagueRepository.save(league);
  }

  public void addTeamToLeague(Long leagueId, Team team){
   League league = leagueRepository.findById(leagueId).get();
   league.getTeams().add(team);
   leagueRepository.save(league);
  }

  public List<Team> getAllTeamsInLeague(Long leagueId){
    return leagueRepository.findById(leagueId).get().getTeams();
  }

  public List<League> findAll(){
    return leagueRepository.findAll();
  }

  public League findNewestLeague(){
    List<League> allLeagues = leagueRepository.findAll();
    return allLeagues.get(allLeagues.size()-1);
  }
}
