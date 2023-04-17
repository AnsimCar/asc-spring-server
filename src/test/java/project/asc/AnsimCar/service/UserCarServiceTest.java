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
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.exception.usercar.UserCarNotFoundException;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.UserCarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static project.asc.AnsimCar.common.fixture.AccountFixture.*;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar1;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar2;

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

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest1();

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

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest1();
        userCarService.addUserCar(account.getId(), userCarCreateRequest);

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        UserCar userCar = userCarRepository.findById(userCarResponse.getId()).get();

        //then
        assertThat(userCar.getId()).isEqualTo(userCarResponse.getId());
    }


    @Test
    @DisplayName("유저Id로_차량_목록_조회")
    void 유저Id로_차량_목록_조회() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        UserCar userCar1 = userCarRepository.save(createUserCar1(account));
        UserCar userCar2 = userCarRepository.save(createUserCar2(account));

        //when
        List<UserCarResponse> userCarResponses = userCarService.findByAccountId(account.getId());

        //then
        assertThat(userCarResponses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저Id로_차량_목록_조회시_차량이_없으면_빈_리스트를_반환한다")
    void 유저Id로_차량_목록_조회시_차량이_없으면_빈_리스트를_반환한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        //when & then
        List<UserCarResponse> userCarResponses = userCarService.findByAccountId(account.getId());
        assertThat(userCarResponses).isEmpty();
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

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest1();

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        UserCarUpdateRequest updateUserCar = UserCarFixture.updateUserCarRequest1();
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

        UserCarCreateRequest userCarCreateRequest = UserCarFixture.createUserCarRequest1();

        UserCarResponse userCarResponse = userCarService.addUserCar(account.getId(), userCarCreateRequest);

        //when
        userCarService.deleteUserCar(account.getId(), userCarResponse.getId());

        //then
        Assertions.assertThrows(UserCarNotFoundException.class, () -> {
            userCarService.findById(userCarResponse.getId());
        });
    }
}