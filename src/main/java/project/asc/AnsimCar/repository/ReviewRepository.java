package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserCar_Id(Long userCarId);

    Optional<Review> findByRent_Id(Long rendId);

    List<Review> findByAccount_Id(Long accountId);
}
