package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "team")
public class Team {
  @Id
  private Long userId;
  @MapsId
  @JoinColumn(name = "user_id")
  @OneToOne(cascade = {CascadeType.ALL}) //1-1 for now, see comment in User.class
  private User user;
  @Column(nullable = false, unique = true)
  private Integer pickNumber;
//  @Column(nullable = false, unique = true)
  private String teamName;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userTeam")
  private Set<F1Driver> drivers = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team team)) return false;
    return userId.equals(team.userId) && pickNumber.equals(team.pickNumber) && Objects.equals(user, team.user) && teamName.equals(team.teamName) && Objects.equals(drivers, team.drivers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, pickNumber, user, teamName, drivers);
  }

  @Override
  public String toString() {
    return "Team{" +
            "id=" + userId +
            ", pickNumber=" + pickNumber +
            ", user=" + user +
            ", teamName='" + teamName + '\'' +
            ", drivers=" + drivers +
            '}';
  }
}
