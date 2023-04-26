package project.asc.AnsimCar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.Status;

import java.util.List;


public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findByAccount_Id(Long accountId);

    Page<Rent> findByStatus(Status status, Pageable pageable);
}
