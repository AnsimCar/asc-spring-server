package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.UserCar;

import java.util.List;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {
    List<UserCar> findByCarModel(String carModel);

    List<UserCar> findByCarCategory(String carCategory);

    List<UserCar> findByManufacturer(String manufacturer);

    List<UserCar> findByFuel(String fuel);


}
