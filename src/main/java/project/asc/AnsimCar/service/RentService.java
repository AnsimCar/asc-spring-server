package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Address;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.address.request.AddressCreateRequest;
import project.asc.AnsimCar.dto.address.response.AddressResponse;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
import project.asc.AnsimCar.exception.account.AccountNotFoundException;
import project.asc.AnsimCar.exception.rent.RentNotFoundException;
import project.asc.AnsimCar.exception.usercar.UserCarNotFoundException;
import project.asc.AnsimCar.exception.usercar.UserCarOwnerException;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.AddressRepository;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.repository.UserCarRepository;
import project.asc.AnsimCar.repository.querydsl.rent.RentCustomRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RentService {

    private final UserCarRepository userCarRepository;
    private final AccountRepository accountRepository;
    private final RentRepository rentRepository;
    private final AddressRepository addressRepository;
    private final RentCustomRepository rentCustomRepository;

    /**
     * 렌트 정보 저장
     */
    public void addRent(Long accountId, Long userCarId, AddressCreateRequest addressCreateRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        UserCar userCar = userCarRepository.findById(userCarId).orElseThrow(UserCarNotFoundException::new);
        Address address = addressRepository.save(addressCreateRequest.toEntity());

        Rent rent = Rent.builder()
                .account(account)
                .userCar(userCar)
                .address(address)
                .registrationDate(LocalDateTime.now())
                .status(Status.AVAILABLE)
                .build();

        rentRepository.save(rent);
    }

    /**
     * 계정Id로 렌트 조회
     */
    //TODO 추후에 사용자가 자신이 등록한 렌트를 확인하는 뷰를 개발할 때 렌트 정보만 띄울지, 차 + 렌트 정보를 같이 띄울지 고민해서 수정이 필요할 것 같다.
    public List<RentResponse> findByUserId(Long accountId) {
        List<Rent> rents = rentRepository.findByAccount_Id(accountId);

        return rents.stream().map(RentResponse::from).toList();
    }

    /**
     * 렌트 상태 수정
     */
    public void updateRentStatus(Long accountId, Long rentId, RentUpdateRequest rentUpdateRequest) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        validateOwner(accountId, rent);

        rent.updateStatus(rentUpdateRequest);
    }

    /**
     * 렌트 대여, 반납 날짜 업데이트
     */
    public void updateRentalReturnDate(Long accountId, Long rentId, RentUpdateRequest rentUpdateRequest) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        validateOwner(accountId, rent);

        rent.updateRentalReturnDate(rentUpdateRequest);
    }

    /**
     * 렌트 삭제
     */
    public void deleteRent(Long accountId, Long rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
        validateOwner(accountId, rent);

        rentRepository.deleteById(rentId);
    }

    public void validateOwner(Long accountId, Rent rent) {
        if (!rent.isOwner(accountId)) {
            throw new UserCarOwnerException();
        }
    }

    /**
     * 모든 렌트카 조회(페이징)
     */
    @Transactional(readOnly = true)
    public Page<RentResponse> findAllPaging(Pageable pageable) {
        return rentRepository.findAll(pageable).map(rent -> new RentResponse(
                rent.getId(),
                rent.getUserCar(),
                AddressResponse.from(rent.getAddress()),
                rent.getRegistrationDate(),
                rent.getStatus(),
                rent.getRentalDate(),
                rent.getReturnDate()
        ));
    }

    /**
     * 렌트 조건 검색
     */
    @Transactional(readOnly = true)
    public Page<RentResponse> findAllComplex(RentSearchRequest request, Pageable pageable) {
        return rentCustomRepository.findAllComplex(request, pageable).map(rent -> new RentResponse(
                rent.getId(),
                rent.getUserCar(),
                AddressResponse.from(rent.getAddress()),
                rent.getRegistrationDate(),
                rent.getStatus(),
                rent.getRentalDate(),
                rent.getReturnDate()
        ));
    }
}
