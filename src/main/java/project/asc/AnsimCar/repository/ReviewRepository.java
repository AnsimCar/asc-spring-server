package project.asc.AnsimCar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserCar_Id(Long userCarId);

    @EntityGraph(attributePaths = {"userCar", "rent", "account"})
    Page<Review> findByUserCar_Id(Long userCarId, Pageable pageable);

    Optional<Review> findByRent_Id(Long rendId);

    List<Review> findByAccount_Id(Long accountId);

    @EntityGraph(attributePaths = {"userCar", "rent", "account"})
    Page<Review> findByAccount_Id(Long accountId, Pageable pageable);
}
