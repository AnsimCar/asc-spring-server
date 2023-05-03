package project.asc.AnsimCar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.RentImage;

import java.util.Optional;

public interface RentImageRepository extends JpaRepository<RentImage, Long> {
    Optional<RentImage> findByRent_Id(Long id);
}
