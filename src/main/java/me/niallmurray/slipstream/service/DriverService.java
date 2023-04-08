package me.niallmurray.slipstream.service;

import jakarta.transaction.Transactional;
import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.domain.League;
import me.niallmurray.slipstream.domain.Team;
import me.niallmurray.slipstream.dto.DriverStanding;
import me.niallmurray.slipstream.repositories.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

  @Autowired
  DriverRepository driverRepository;

  // Gets drivers info including points for whole season from API and stores in DB.
  // Should probably be moved to admin service/endpoint?
  // This can be called to update points after races, but may need tweaking.
  // Should do separate method just to update driver list after races.
  public List<Driver> mapDTOToDrivers(List<DriverStanding> allDriverStandings) {
    List<Driver> drivers = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    for (DriverStanding driverStanding : allDriverStandings) {
      Driver driver = modelMapper.map(driverStanding.driverDTO, Driver.class);
      driver.setStanding(Integer.parseInt(driverStanding.position));
      driver.setPoints(Double.parseDouble(driverStanding.points));
      driver.setConstructor(driverStanding.constructors.get(0).name);
      drivers.add(driver);
    }
    // To store drivers randomly
//    Collections.shuffle(drivers);
    return drivers;
  }


//  public void addConstructors(){
//    List<Driver> drivers = driverRepository.findAll();
//    for (Driver driver : drivers){
//      driver.setConstructor(driverStanding.constructors.get(0).name);
//
//    }
//  }


  public void addDrivers(List<Driver> drivers) {
//  ModelMapper modelMapper = new ModelMapper();
//  // To store drivers randomly
//  Collections.shuffle(allDriverStandings);
//  for (DriverStanding driverStanding: allDriverStandings) {
//    Driver driver = modelMapper.map(driverStanding.driverDTO, Driver.class);
//    driver.setStanding(Integer.parseInt(driverStanding.position));
//    driver.setPoints(Double.parseDouble(driverStanding.points));
    for (Driver driver : drivers) {
      // Set current champ to car #1
      if (driver.getCarNumber() == 33) {
        driver.setCarNumber(1);
      }
      if (driverRepository.findByCarNumber(driver.getCarNumber()) == null) {
        driverRepository.save(driver);
      }
    }
  }

  @Transactional
  public void updateDrivers(List<Driver> updatedDrivers) {
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
    for (Driver driver : updatedDrivers) {
      driverRepository.updatePoints(driver.getShortName(), driver.getPoints());
      driverRepository.updateStandings(driver.getShortName(), driver.getStanding());
    }
//    driverRepository.saveAll(updatedDrivers);
  }

  public List<Driver> sortDriversStanding() {
    return driverRepository.findAllByOrderByStandingAsc();
  }

  public List<Driver> getUndraftedDrivers(League league) {
    List<Driver> undraftedDrivers = driverRepository.findAllByOrderByStandingAsc();
    List<Team> teams = league.getTeams();
    for (Team team : teams) {
      List<Driver> drivers = team.getDrivers();
      undraftedDrivers.removeAll(drivers);
    }
    return undraftedDrivers;
  }

  public List<Driver> findAll() {
    return driverRepository.findAll();
  }

  public Driver findById(Long driverId) {
    return driverRepository.findById(driverId).orElse(null);
  }
}
