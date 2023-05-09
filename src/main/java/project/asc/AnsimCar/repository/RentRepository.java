package project.asc.AnsimCar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.repository.querydsl.rent.RentRepositoryCustom;

import java.util.Optional;


public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {

    Page<Rent> findByAccount_Id(Long accountId, Pageable pageable);

    Page<Rent> findByStatusAndAccount_IdNot(Status status, Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"userCar", "account", "address"})
    Optional<Rent> findEntityGraphById(Long id);

    @EntityGraph(attributePaths = {"userCar", "account", "address"})
    Page<Rent> findByRentAccount_Id(Long rentAccountId, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"userCar", "account", "address"})
    Optional<Rent> findById(Long id);

//    @EntityGraph(attributePaths = {"userCar", "account", "address"})
//    Optional<Rent> findByRentAccountIdAndStatusOrStatus(Long rentAccountId, Status status1, Status status2);

}
