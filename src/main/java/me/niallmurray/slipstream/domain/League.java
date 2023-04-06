package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "league")
public class League {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long leagueId;
  @Column(nullable = false, unique = true)
  private String leagueName;
//  @JoinColumn(name = "team_id")
  @OneToMany(fetch = FetchType.LAZY,
          cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
          mappedBy = "teamId")
  private Set<Team> teams = new HashSet<>();
}
