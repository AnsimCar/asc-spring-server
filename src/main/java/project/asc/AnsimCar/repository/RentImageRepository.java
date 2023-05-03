package project.asc.AnsimCar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.BeforeImage;

public interface RentImageRepository extends JpaRepository<BeforeImage, Long> {
}
