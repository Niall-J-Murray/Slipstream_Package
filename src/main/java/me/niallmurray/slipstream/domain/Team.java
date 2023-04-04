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
  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}) //1-1 for now, see comment in User.class
  private User user;
  @Column(nullable = false, unique = true)
  private Integer firstPickNumber;
  @Column(nullable = false, unique = true)
  private Integer secondPickNumber;
  @Column(nullable = false, unique = true)
  private String teamName;
  @Column()
  @OneToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
          mappedBy = "team")
  private Set<Driver> drivers = new HashSet<>(6);

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team team)) return false;
    return Objects.equals(userId, team.userId) && Objects.equals(user, team.user) && Objects.equals(firstPickNumber, team.firstPickNumber) && Objects.equals(secondPickNumber, team.secondPickNumber) && Objects.equals(teamName, team.teamName) && Objects.equals(drivers, team.drivers);
  }

    //Issue with hashcode endless call loops
//  @Override
//  public int hashCode() {
//    return Objects.hash(userId, user, firstPickNumber, secondPickNumber, teamName, drivers);
//  }

//  @Override
//  public String toString() {
//    return "Team Name: '" + teamName + '\''
//            +  System.lineSeparator() +
//            "Drivers: " + drivers;
//  }


  @Override
  public String toString() {
    return "Team{" +
            "id=" + userId +
            ", pickNumber=" + firstPickNumber +
            ", user=" + user +
            ", teamName='" + teamName + '\'' +
            ", drivers=" + drivers +
            '}';
  }
}
