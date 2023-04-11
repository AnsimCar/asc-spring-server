package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import java.util.List;

public interface UserCarRepository extends JpaRepository<UserCar, Long> {
    List<UserCar> findByCarModel(String carModel);

    List<UserCar> findByCarCategory(CarCategory carCategory);

    List<UserCar> findByManufacturer(String manufacturer);

    List<UserCar> findByFuel(Fuel fuel);


}
