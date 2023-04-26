package project.asc.AnsimCar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.repository.querydsl.rent.RentRepositoryCustom;

import java.util.List;
import java.util.Optional;


public interface RentRepository extends JpaRepository<Rent, Long>, RentRepositoryCustom {

    List<Rent> findByAccount_Id(Long accountId);

    Optional<Rent> findByUserCar_Id(Long userCarId);

    Page<Rent> findByStatus(Status status, Pageable pageable);
}
