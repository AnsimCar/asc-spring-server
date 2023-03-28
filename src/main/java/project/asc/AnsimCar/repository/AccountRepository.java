package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

}
