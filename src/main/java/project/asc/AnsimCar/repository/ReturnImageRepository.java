package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.AfterImage;

public interface ReturnImageRepository extends JpaRepository<AfterImage, Long> {
}
