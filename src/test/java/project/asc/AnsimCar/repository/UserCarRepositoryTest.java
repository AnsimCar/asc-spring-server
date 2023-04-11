package project.asc.AnsimCar.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.RepositoryTest;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar;

class UserCarRepositoryTest extends RepositoryTest {
    @Autowired
    UserCarRepository userCarRepository;
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        //더미 데이터
        Account saveAccount = accountRepository.save(Account.of("Woo", "jun@naver.com", "ppp", "0102222", 25));
        userCarRepository.save(UserCar.of(saveAccount, "고스트", CarCategory.SEMI_FORMAL, "롤스로이스", Fuel.DIESEL, "111우 2222"));
    }

    @Test
    @DisplayName("차모델로_조회")
    void findByCarModel() {
        //given
        Account saveAccount = accountRepository.save(createAccount());
        UserCar userCar1 = userCarRepository.save(createUserCar(saveAccount));
        userCarRepository.save(createUserCar(saveAccount));

        //when
        List<UserCar> findCar = userCarRepository.findByCarModel(userCar1.getCarModel());

        //then
        assertThat(findCar.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("차종으로_조회")
    void findByCarCategory() {
        //given
        Account saveAccount = accountRepository.save(createAccount());
        UserCar userCar1 = userCarRepository.save(createUserCar(saveAccount));
        UserCar userCar2 =  userCarRepository.save(createUserCar(saveAccount));

        //when
        List<UserCar> findCar = userCarRepository.findByCarCategory(userCar1.getCarCategory());

        //then
        assertThat(findCar.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("제조사로_조회")
    void findByManufacturer() {
        //given
        Account saveAccount = accountRepository.save(createAccount());
        UserCar userCar1 = userCarRepository.save(createUserCar(saveAccount));
        userCarRepository.save(createUserCar(saveAccount));

        //when
        List<UserCar> findCar = userCarRepository.findByManufacturer(userCar1.getManufacturer());

        //then
        assertThat(findCar.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("연료로_조회")
    void findByFuel() {
        //given
        Account saveAccount = accountRepository.save(createAccount());
        UserCar userCar1 = userCarRepository.save(createUserCar(saveAccount));
        userCarRepository.save(createUserCar(saveAccount));

        //when
        List<UserCar> findCar = userCarRepository.findByFuel(userCar1.getFuel());

        //then
        assertThat(findCar.size()).isEqualTo(2);
    }
}