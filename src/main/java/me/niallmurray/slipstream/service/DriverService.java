package me.niallmurray.slipstream.service;

import me.niallmurray.slipstream.domain.Driver;
import me.niallmurray.slipstream.dto.DriverStanding;
import me.niallmurray.slipstream.repositories.DriverRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DriverService {

  @Autowired
  DriverRepository driverRepository;

  // Gets drivers info including points for whole season from API and stores in DB.
  // Should probably be moved to admin service/endpoint?
  // This can be called to update points after races, but may need tweaking.
  // Should do separate method just to update driver list after races.
public void addDrivers(List<DriverStanding> allDriverStandings){
  ModelMapper modelMapper = new ModelMapper();
  // To store drivers randomly
  Collections.shuffle(allDriverStandings);
  for (DriverStanding driverStanding: allDriverStandings) {
    Driver driver = modelMapper.map(driverStanding.driverDTO, Driver.class);
    driver.setStanding(Integer.parseInt(driverStanding.position));
    driver.setPoints(Double.parseDouble(driverStanding.points));

    // Set current champ to car #1
    if (driver.getCarNumber() == 33){
      driver.setCarNumber(1);
    }
    if (driverRepository.findById(driver.getDriverId()).isPresent()){
      //check current points and update if different.
    }
    if (driverRepository.findByCarNumber(driver.getCarNumber()) == null) {
      driverRepository.save(driver);
    }
  }
}

  public List<Driver> findAll() {
  return driverRepository.findAll();
  }
}
