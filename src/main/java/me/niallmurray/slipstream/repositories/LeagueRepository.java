package me.niallmurray.slipstream.repositories;

import jakarta.transaction.Transactional;
import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League, Long> {
  @Transactional
  @Modifying
  @Query("update League l set l.teams = :teams where l.leagueId = :leagueId")
  void updateTeamsInLeague
          (@Param(value = "leagueId") Long leagueId,
           @Param(value = "teams") List<Team> teams);

  @Transactional
  @Modifying
  @Query("update League l set l.teams = :teams where l.leagueId = :leagueId")
  void updateOneTeamInLeague
          (@Param(value = "leagueId") Long leagueId,
           @Param(value = "teams") Team team);
}
