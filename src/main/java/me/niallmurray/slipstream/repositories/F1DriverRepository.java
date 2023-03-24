package me.niallmurray.slipstream.repositories;

import me.niallmurray.slipstream.domain.F1Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public interface F1DriverRepository extends JpaRepository<F1Driver, Long> {
  Set<F1Driver> drivers = new HashSet<>(25);

  default Set<F1Driver> addDriversManually() throws FileNotFoundException {
    File file = new File("C:\\Users\\Niall\\Documents\\slipstream\\docs\\driver list.txt");
    Scanner sc = new Scanner(file);
    while (sc.hasNextLine()) {
      for (F1Driver driver : drivers) {
        driver.setName(sc.nextLine());
      }
    }
    System.out.println(drivers);

    return drivers;
  }


}
