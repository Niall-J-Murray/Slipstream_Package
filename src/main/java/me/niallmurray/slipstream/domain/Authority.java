package me.niallmurray.slipstream.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "authority")
public class Authority implements GrantedAuthority {
  @Serial
  private static final long serialVersionUID = -8123526131047887755L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long authId;
  @ManyToOne
  private User user;
  @Column(nullable = false)
  private String authority;

//  @Override
//  public String toString() {
//    return "Authority:" +
//            " id= " + id +
//            ", user= " + user +
//            ", authority= " + authority;
//  }

  @Override
  public int hashCode() {
    return Objects.hash(authId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Authority other)) {
      return false;
    }
    return Objects.equals(authId, other.authId);
  }


//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//
//    Authority authority1 = (Authority) o;
//
//    if (!Objects.equals(authId, authority1.authId)) return false;
//    if (!Objects.equals(user, authority1.user)) return false;
//    return Objects.equals(authority, authority1.authority);
//  }
//
//  @Override
//  public int hashCode() {
//    int result = authId != null ? authId.hashCode() : 0;
//    result = 31 * result + (user != null ? user.hashCode() : 0);
//    result = 31 * result + (authority != null ? authority.hashCode() : 0);
//    return result;
//  }
}
