package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.User;
import me.niallmurray.slipstream.repositories.DriverRepository;
import me.niallmurray.slipstream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private DriverRepository driverRepository;

  @Secured({"ROLE_ADMIN"})
  public List<User> getAllUserAccounts() {
    return userRepository.findAll();
  }

  @Secured({"ROLE_ADMIN"})
  public void updateDriverStandings(List<Driver> updatedDrivers) {
//    List<Driver> currentDrivers = driverRepository.findAll();
//    currentDrivers.stream()
//            .forEach(d1 -> {
//              Driver d2 = updatedDrivers.iterator().next();
//              if (d1.getDriverId().equals(d2.getDriverId())) {
//                if (d1.getPoints().equals(d2.getPoints())) {
//                  d1.setPoints(d2.getPoints());
//                }
//                if (d1.getStanding() != d2.getStanding()) {
//                  d1.setStanding(d2.getStanding());
//                }
//                driverRepository.save(d1);
//              }
//            });

    driverRepository.saveAll(updatedDrivers);
  }
}
