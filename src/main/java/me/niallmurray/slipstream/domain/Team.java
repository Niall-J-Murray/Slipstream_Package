package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "team")
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teamId;
  @JoinColumn(name = "user_id")
  @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
  //1-1 for now, see comment in User.class
  private User user;
  @Column(nullable = false, unique = true)
  private Integer firstPickNumber;
  @Column(nullable = false, unique = true)
  private Integer secondPickNumber;
  @Column(nullable = false, unique = true)
  private String teamName;
  @Column()
  private Double teamPoints;
  @Column()
  private Integer ranking;
  @Column()
  @OneToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
          mappedBy = "team")
  private List<Driver> drivers = new ArrayList<>(6);
  @ManyToOne()
  League league;

//  @Override
//  public String toString() {
//    return "Team{" +
//            "teamId=" + teamId +
//            ", user=" + user +
//            ", firstPickNumber=" + firstPickNumber +
//            ", secondPickNumber=" + secondPickNumber +
//            ", teamName='" + teamName + '\'' +
//            ", drivers=" + drivers +
//            '}';
//  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (!(o instanceof Team team)) return false;
//    return Objects.equals(teamId, team.teamId) && Objects.equals(user, team.user) && Objects.equals(firstPickNumber, team.firstPickNumber) && Objects.equals(secondPickNumber, team.secondPickNumber) && Objects.equals(teamName, team.teamName) && Objects.equals(drivers, team.drivers);
//  }
//
//  @Override
//  public int hashCode() {
//    return Objects.hash(teamId, user, firstPickNumber, secondPickNumber, teamName, drivers);
//  }

  @Override
  public String toString() {
    return "Team:" +
            " teamId= " + teamId +
            ", user= " + user.getUsername() +
            ", teamName=' " + teamName + '\'' +
            ", firstPick= " + firstPickNumber +
            ", secondPick= " + secondPickNumber;

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Team team)) return false;
    return Objects.equals(teamId, team.teamId) && Objects.equals(teamName, team.teamName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(teamId,teamName);
  }
}
