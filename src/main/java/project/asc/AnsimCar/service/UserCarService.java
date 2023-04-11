package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.dto.account.AccountDto;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.exception.Account.AccountNotFoundException;
import project.asc.AnsimCar.exception.UserCar.UserCarNotFoundException;
import project.asc.AnsimCar.exception.UserCar.UserCarOwnerException;
import project.asc.AnsimCar.repository.AccountRepository;
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
    @Transactional
    public UserCarResponse addUserCar(final AccountDto accountDto, final UserCarCreateRequest userCarCreateRequest) {
        Account findAccount = findAccount(accountDto);
        UserCar userCar = createUserCar(findAccount, userCarCreateRequest);

        return UserCarResponse.from(userCarRepository.save(userCar));
    }

    private UserCar createUserCar(Account findAccount, UserCarCreateRequest userCarCreateRequest) {
        return UserCar.of(
                findAccount,
                userCarCreateRequest
        );
    }

    private Account findAccount(AccountDto accountDto) {
        return accountRepository.findById(accountDto.getId()).orElseThrow(() -> new AccountNotFoundException());
    }

    /**
     * ID 검색
     */
    public UserCarResponse findById(Long id) {
        UserCar userCar = userCarRepository.findById(id).orElseThrow(() -> new UserCarNotFoundException());
        return UserCarResponse.from(userCar);
    }

    /**
     * 모델명 검색
     */
    public List<UserCarResponse> findByCarModel(String carModel) {
        List<UserCar> userCars = userCarRepository.findByCarModel(carModel);
        if (userCars.isEmpty())
            new UserCarNotFoundException();

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
            new UserCarNotFoundException();

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
            new UserCarNotFoundException();

        List<UserCarResponse> userCarResponseList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarResponseList.add(UserCarResponse.from(userCar));
        }

        return userCarResponseList;
    }

    /**
     * 차량 업데이트
     */
    @Transactional
    public void updateUserCar(AccountDto accountDto, Long userCarId, UserCarUpdateRequest userCarUpdateRequest) {
        UserCar userCar = findUserCar(userCarId);
        validateOwner(accountDto, userCar);

        userCar.updateUserCar(userCarUpdateRequest);
    }

    private UserCar findUserCar(Long userCarId) {
        return userCarRepository.findById(userCarId).orElseThrow(() -> new UserCarNotFoundException());
    }

    /**
     * 차량 삭제
     */
    @Transactional
    public void deleteUserCar(AccountDto accountDto, Long userCarId) {
        UserCar userCar = findUserCar(userCarId);
        validateOwner(accountDto, userCar);

        userCarRepository.delete(userCar);
    }

    private void validateOwner(AccountDto account, UserCar userCar) {
        if (!userCar.isOwner(account.getId())) {
            throw new UserCarOwnerException();
        }
    }
}
