package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "driver")
public class F1Driver {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long driverId;
  @Column(nullable = false, unique = true)
  private String name;
  @Column()
  private Double points;
  @Column()
  private Integer standing;
  @ManyToOne
  private Team userTeam;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof F1Driver f1Driver)) return false;
    return Objects.equals(driverId, f1Driver.driverId) && Objects.equals(name, f1Driver.name) && Objects.equals(points, f1Driver.points) && Objects.equals(standing, f1Driver.standing) && Objects.equals(userTeam, f1Driver.userTeam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(driverId, name, points, standing, userTeam);
  }

  @Override
  public String toString() {
    return "F1Driver{" +
            "driverId=" + driverId +
            ", name='" + name + '\'' +
            ", points=" + points +
            ", standing=" + standing +
            ", userTeam=" + userTeam +
            '}';
  }
}
