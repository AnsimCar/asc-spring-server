package project.asc.AnsimCar.repository.querydsl.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;

import java.util.Optional;

public interface RentRepositoryCustom {
    Page<Rent> findAllComplex(RentSearchRequest request, Pageable pageable);

    Optional<Rent> findInfoById(Long id);
}
