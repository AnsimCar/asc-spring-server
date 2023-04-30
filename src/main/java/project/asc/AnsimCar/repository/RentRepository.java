package project.asc.AnsimCar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.repository.querydsl.rent.RentRepositoryCustom;

import java.util.List;
import java.util.Optional;


public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {

    Page<Rent> findByAccount_Id(Long accountId, Pageable pageable);

    Page<Rent> findByStatus(Status status, Pageable pageable);

    @EntityGraph(attributePaths = {"userCar", "account", "address"})
    Optional<Rent> findEntityGraphById(Long id);

    Page<Rent> findByRentAccount_Id(Long rentAccountId, Pageable pageable);

//    @Query("select r from Rent r left join fetch r.userCar left join fetch r.account left join fetch r.address")
//    Optional<Rent> findFetchById(Long id);
}
