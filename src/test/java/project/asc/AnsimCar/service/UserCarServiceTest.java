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
import project.asc.AnsimCar.dto.account.AccountDto;
import project.asc.AnsimCar.dto.account.AccountRequest;
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
        Account saveAccount = accountRepository.save(Account.of("Woo", "jun@naver.com", "ppp", "0102222", 25));
        userCarRepository.save(UserCar.of(saveAccount, "고스트", CarCategory.SEMI_FORMAL, "롤스로이스", Fuel.DIESEL, "111우 2222"));
    }

    @Test
    @DisplayName("차량 저장")
    void save() {
        //given
        AccountRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);
        Account account = accountRepository.findByEmail(accountRequest.email()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        //when
        UserCarResponse userCarResponse = userCarService.addUserCar(AccountDto.from(account), userCarCreateRequest);

        //then
        assertThat(userCarRepository.findById(userCarResponse.getId()).get().getCarNumber()).isEqualTo(userCarCreateRequest.getCarNumber());
    }

    @Test
    @DisplayName("차량 검색")
    void findById() {
        //given
        AccountRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);
        Account account = accountRepository.findByEmail(accountRequest.email()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();
        userCarService.addUserCar(AccountDto.from(account), userCarCreateRequest);

        UserCarResponse userCarResponse = userCarService.addUserCar(AccountDto.from(account), userCarCreateRequest);

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
        AccountRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);
        Account account = accountRepository.findByEmail(accountRequest.email()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        UserCarResponse userCarResponse = userCarService.addUserCar(AccountDto.from(account), userCarCreateRequest);

        //when
        UserCarUpdateRequest updateUserCar = UserCarFixture.updateUserCarRequest();
        userCarService.updateUserCar(AccountDto.from(account), userCarResponse.getId(), updateUserCar);

        //then
        UserCarResponse findCar = userCarService.findById(userCarResponse.getId());
        assertThat(findCar.getCarModel()).isEqualTo(updateUserCar.getCarModel());
    }

    @Test
    @DisplayName("차량 삭제")
    void delete() {
        //given
        AccountRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);
        Account account = accountRepository.findByEmail(accountRequest.email()).get();

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest();

        UserCarResponse userCarResponse = userCarService.addUserCar(AccountDto.from(account), userCarCreateRequest);

        //when
        userCarService.deleteUserCar(AccountDto.from(account), userCarResponse.getId());

        //then
        Assertions.assertThrows(UserCarNotFoundException.class, () -> {
            userCarService.findById(userCarResponse.getId());
        });
    }
}