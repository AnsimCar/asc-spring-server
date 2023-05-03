package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.ReturnImage;

import java.util.Optional;

public interface ReturnImageRepository extends JpaRepository<ReturnImage, Long> {
    Optional<ReturnImage> findByRent_Id(Long id);
}
