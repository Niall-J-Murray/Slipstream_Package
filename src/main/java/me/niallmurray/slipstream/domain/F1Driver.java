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
  @ManyToOne
  private Team userTeam;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof F1Driver f1Driver)) return false;
    return driverId.equals(f1Driver.driverId) && name.equals(f1Driver.name) && Objects.equals(points, f1Driver.points) && Objects.equals(userTeam, f1Driver.userTeam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(driverId, name, points, userTeam);
  }

  @Override
  public String toString() {
    return "F1Driver{" +
            "id=" + driverId +
            ", name='" + name + '\'' +
            ", points=" + points +
            ", userTeam=" + userTeam +
            '}';
  }
}
