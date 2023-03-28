package project.asc.AnsimCar.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.RepositoryTest;
import project.asc.AnsimCar.domain.Account;

import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AccountFixture.이메일;


class AccountRepositoryTest extends RepositoryTest {

    @Autowired AccountRepository accountRepository;

    @Test
    @DisplayName("이메일로_계정을_조회한다")
    public void 이메일로_계정을_조회한다() {
        //given
        Account saveAccount = accountRepository.save(createAccount());

        //when
        Account findAccount = accountRepository.findByEmail(saveAccount.getEmail()).get();

        //then
        Assertions.assertThat(findAccount).isEqualTo(saveAccount);
    }


}