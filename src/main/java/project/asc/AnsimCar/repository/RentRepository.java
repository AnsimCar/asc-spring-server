package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Rent;

import java.util.List;


public interface RentRepository extends JpaRepository<Rent, Long> {

    List<Rent> findByAccount_Id(Long accountId);
}
