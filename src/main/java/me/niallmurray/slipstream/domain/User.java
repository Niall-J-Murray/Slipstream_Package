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
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
  private Set<Authority> authorities = new HashSet<>();
  @Column()
  private String lastLogout;
  @OneToOne(mappedBy = "user",
          cascade = {CascadeType.ALL},
          orphanRemoval = true)
  private Team team;
  // For simplicity, each user has only one team for now.
  // This means no need for creating separate leagues for users with multiple teams.
  // This also limits the app to 10 users at one time for now,
  // but could separate schemas be used for multiple leagues of 10 users?
  // Features for multiple teams/leagues per user, and/or drivers per team could be added eventually.

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    return Objects.equals(userId, other.userId);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + userId +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", authorities=" + authorities +
            ", lastLogout='" + lastLogout + '\'' +
//            ", team=" + team +
            '}';
  }
}
