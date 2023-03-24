package me.niallmurray.slipstream.repositories;


import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TeamRepository extends JpaRepository<Team, Long> {
  @Query("select u from User u"
          + " left join fetch u.team"
          + " where u.team = :teamName")
  User findTeamByTeamName(String teamName);

}
