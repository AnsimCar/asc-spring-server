package project.asc.AnsimCar.repository.querydsl.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;

public interface RentRepositoryCustom {
    Page<Rent> findAllComplex(RentSearchRequest request, Pageable pageable);
}
