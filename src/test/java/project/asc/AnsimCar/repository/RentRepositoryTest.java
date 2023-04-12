package project.asc.AnsimCar.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.RepositoryTest;
import project.asc.AnsimCar.common.fixture.RentFixture;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Address;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AddressFixture.createAddress;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar1;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar2;

class RentRepositoryTest extends RepositoryTest {

    @Autowired AccountRepository accountRepository;
    @Autowired UserCarRepository userCarRepository;

    @Autowired AddressRepository addressRepository;
    @Autowired RentRepository rentRepository;


    @Test
    @DisplayName("계정Id로_렌트_목록을_조회한다")
    void 계정Id로_렌트_목록을_조회한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        UserCar userCar1 = userCarRepository.save(createUserCar1(account));
        UserCar userCar2 = userCarRepository.save(createUserCar2(account));

        Address address = addressRepository.save(createAddress());

        Rent rent1 = RentFixture.createRent(userCar1, account, address);
        Rent rent2 = RentFixture.createRent(userCar2, account, address);
        rentRepository.save(rent1);
        rentRepository.save(rent2);

        //when
        List<Rent> rents = rentRepository.findByAccount_Id(account.getId());

        //then
        assertThat(rents.size()).isEqualTo(2);
    }
}