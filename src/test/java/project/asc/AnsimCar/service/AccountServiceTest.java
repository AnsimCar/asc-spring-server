package project.asc.AnsimCar.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.common.fixture.AccountFixture;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.AccountRequest;
import project.asc.AnsimCar.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccountRequest;


class AccountServiceTest extends ServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;


    @Test
    @DisplayName("회원가입_성공")
    public void 회원가입_성공() {
        //given
        AccountRequest accountRequest = createAccountRequest();

        //when
        accountService.register(accountRequest);

        //then
        Account account = accountRepository.findByEmail(accountRequest.email()).get();
        assertThat(accountRequest.username()).isEqualTo(account.getUsername());
    }

    @Test
    @DisplayName("이메일이 중복이면 회원가입이 불가능하고 예외를 던진다")
    public void 이메일이_중복이면_회원가입이_불가능하고_예외를_던진다() {
        //given
        AccountRequest accountRequest = createAccountRequest();
        accountService.register(accountRequest);

        //when & then
        assertThatThrownBy(
                () -> accountService.register(accountRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}