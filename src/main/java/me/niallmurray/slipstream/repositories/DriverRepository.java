package me.niallmurray.slipstream.repositories;

import me.niallmurray.slipstream.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

  Driver findByCarNumber(int carNumber);




}

//  Set<Driver> drivers = new HashSet<>(25);
//
//  default Set<Driver> addDriversManually() throws FileNotFoundException {
//    File file = new File("C:\\Users\\Niall\\Documents\\slipstream\\docs\\driver list.txt");
//    Scanner sc = new Scanner(file);
//    while (sc.hasNextLine()) {
//      for (Driver driver : drivers) {
//        driver.setName(sc.nextLine());
//      }
//    }
//    System.out.println(drivers);
//
//    return drivers;
//  }