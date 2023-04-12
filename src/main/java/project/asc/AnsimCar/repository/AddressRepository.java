package project.asc.AnsimCar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.asc.AnsimCar.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
