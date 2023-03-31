package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "driver")
public class Driver {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long driverId;
  //  @Column(nullable = false, unique = true)
  @Column()
  private int carNumber;
  @Column()
  private String shortName;
  @Column()
  private String wikiPage;
  @Column()
  private String firstName;
  @Column()
  private String surname;
  @Column()
  private String dateOfBirth;
  @Column()
  private String nationality;
  @Column()
  private Double points;
  @Column()
  private int standing;
  @ManyToOne
  private Team userTeam;

// Trying to remove decimal point
//  public double getPoints() {
//    if (points%1!=0){
//      return points;
//    }
//    return points.intValue();
//  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Driver driver)) return false;
    return Objects.equals(driverId, driver.driverId) && Objects.equals(carNumber, driver.carNumber) && Objects.equals(shortName, driver.shortName) && Objects.equals(wikiPage, driver.wikiPage) && Objects.equals(firstName, driver.firstName) && Objects.equals(surname, driver.surname) && Objects.equals(dateOfBirth, driver.dateOfBirth) && Objects.equals(nationality, driver.nationality) && Objects.equals(points, driver.points) && Objects.equals(standing, driver.standing) && Objects.equals(userTeam, driver.userTeam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(driverId, carNumber, shortName, wikiPage, firstName, surname, dateOfBirth, nationality, points, standing, userTeam);
  }

  @Override
  public String toString() {
    return "Driver{" +
            "driverId=" + driverId +
            ", carNumber='" + carNumber + '\'' +
            ", shortName='" + shortName + '\'' +
            ", wikiPage='" + wikiPage + '\'' +
            ", firstName='" + firstName + '\'' +
            ", surname='" + surname + '\'' +
            ", dateOfBirth='" + dateOfBirth + '\'' +
            ", nationality='" + nationality + '\'' +
            ", points=" + points.intValue() +
            ", standing=" + standing +
            ", userTeam=" + userTeam +
            '}';
  }
}
