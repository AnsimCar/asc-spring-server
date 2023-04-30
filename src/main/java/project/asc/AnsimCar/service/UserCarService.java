package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.exception.account.AccountNotFoundException;
import project.asc.AnsimCar.exception.usercar.UserCarNotFoundException;
import project.asc.AnsimCar.exception.usercar.UserCarOwnerException;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.repository.UserCarRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCarService {
    private final UserCarRepository userCarRepository;
    private final AccountRepository accountRepository;

    /**
     * 차량 등록
     */
    public UserCarResponse addUserCar(Long accountId, final UserCarCreateRequest userCarCreateRequest) {
        Account findAccount = findAccount(accountId);
        UserCar userCar = createUserCar(findAccount, userCarCreateRequest);

        return UserCarResponse.from(userCarRepository.save(userCar));
    }

    private UserCar createUserCar(Account account, UserCarCreateRequest userCarCreateRequest) {
        return UserCar.builder()
                .account(account)
                .carModel(userCarCreateRequest.getCarModel())
                .carCategory(userCarCreateRequest.getCarCategory())
                .manufacturer(userCarCreateRequest.getManufacturer())
                .fuel(userCarCreateRequest.getFuel())
                .carNumber(userCarCreateRequest.getCarNumber())
                .usable(true)
                .build();

    }

    private Account findAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }

    /**
     * ID 검색
     */
    public UserCarResponse findById(Long id) {
        UserCar userCar = userCarRepository.findById(id).orElseThrow(UserCarNotFoundException::new);
        return UserCarResponse.from(userCar);
    }

    /**
     * 계정Id로 검색
     */
    public List<UserCarResponse> findByAccountId(Long accountId) {
        List<UserCar> userCars = userCarRepository.findByAccount_Id(accountId);

        return userCars.stream().map(UserCarResponse::from).toList();
    }

    /**
     * 모델명 검색
     */
    public List<UserCarResponse> findByCarModel(String carModel) {
        List<UserCar> userCars = userCarRepository.findByCarModel(carModel);
        if (userCars.isEmpty())
            throw new UserCarNotFoundException();

        List<UserCarResponse> userCarResponseList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarResponseList.add(UserCarResponse.from(userCar));
        }

        return userCarResponseList;
    }

    /**
     * 차종 검색
     */
    public List<UserCarResponse> findByCarCategory(CarCategory carCategory) {
        List<UserCar> userCars = userCarRepository.findByCarCategory(carCategory);
        if (userCars.isEmpty())
            throw new UserCarNotFoundException();

        List<UserCarResponse> userCarResponseList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarResponseList.add(UserCarResponse.from(userCar));
        }

        return userCarResponseList;
    }

    /**
     * 제조사 검색
     */
    public List<UserCarResponse> findByCarCategory(String manufacturer) {
        List<UserCar> userCars = userCarRepository.findByManufacturer(manufacturer);
        if (userCars.isEmpty())
            throw new UserCarNotFoundException();

        List<UserCarResponse> userCarResponseList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarResponseList.add(UserCarResponse.from(userCar));
        }

        return userCarResponseList;
    }

    /**
     * 차량 업데이트
     */
    public void updateUserCar(Long accountId, Long userCarId, UserCarUpdateRequest userCarUpdateRequest) {
        UserCar userCar = findUserCar(userCarId);
        validateOwner(accountId, userCar);

        userCar.updateUserCar(userCarUpdateRequest);
    }

    private UserCar findUserCar(Long userCarId) {
        return userCarRepository.findById(userCarId).orElseThrow(UserCarNotFoundException::new);
    }

    /**
     * 차량 삭제
     */
    public void deleteUserCar(Long accountId, Long userCarId) {
        UserCar userCar = findUserCar(userCarId);
        validateOwner(accountId, userCar);

        userCarRepository.delete(userCar);
    }

    private void validateOwner(Long accountId, UserCar userCar) {
        if (!userCar.isOwner(accountId)) {
            throw new UserCarOwnerException();
        }
    }
}
