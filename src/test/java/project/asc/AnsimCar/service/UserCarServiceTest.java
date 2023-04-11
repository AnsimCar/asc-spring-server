package project.asc.AnsimCar.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.common.fixture.UserCarFixture;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.exception.UserCar.UserCarNotFoundException;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.UserCarRepository;

import static org.assertj.core.api.Assertions.*;
import static project.asc.AnsimCar.common.fixture.AccountFixture.*;

class UserCarServiceTest extends ServiceTest {

    @Autowired
    UserCarService userCarService;

    @Autowired
    UserCarRepository userCarRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        //더미 데이터
        Account saveAccount = accountRepository.save(Account.builder().username("Woo").email("jun@naver.com").password("ppp").phoneNumber("0102222").age(25).build());
        userCarRepository.save(UserCar.builder().account(saveAccount).carModel("고스트").carCategory(CarCategory.SEMI_FORMAL).manufacturer("롤스로이스").fuel(Fuel.DIESEL).carNumber("111우 2222").build());
    }

    @Test
    @DisplayName("차량 저장")
    void save() {
        //given
        AccountCreateRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);
        Account account = accountRepository.findByEmail(accountRequest.getEmail()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        //when
        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //then
        assertThat(userCarRepository.findById(userCarResponse.getId()).get().getCarNumber()).isEqualTo(userCarCreateRequest.getCarNumber());
    }

    @Test
    @DisplayName("차량 검색")
    void findById() {
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();
        userCarService.addUserCar(account.getId(), userCarCreateRequest);

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        UserCar userCar = userCarRepository.findById(userCarResponse.getId()).get();

        //then
        assertThat(userCar.getId()).isEqualTo(userCarResponse.getId());
    }

    @Test
    void findByCarModel() {
    }

    @Test
    void findByCarCategory() {
    }

    @Test
    void testFindByCarCategory() {
    }

    @Test
    @DisplayName("차량 수정")
    void updateUserCar() {
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        UserCarUpdateRequest updateUserCar = UserCarFixture.updateUserCarRequest();
        userCarService.updateUserCar(account.getId(), userCarResponse.getId(), updateUserCar);

        //then
        UserCarResponse findCar = userCarService.findById(userCarResponse.getId());
        assertThat(findCar.getCarModel()).isEqualTo(updateUserCar.getCarModel());
    }

    @Test
    @DisplayName("차량 삭제")
    void delete() {
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        userCarService.deleteUserCar(account.getId(), userCarResponse.getId());

        //then
        Assertions.assertThrows(UserCarNotFoundException.class, () -> {
            userCarService.findById(userCarResponse.getId());
        });
    }
}