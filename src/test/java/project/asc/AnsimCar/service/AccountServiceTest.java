package project.asc.AnsimCar.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.domain.Account;

import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.exception.Account.EmailExistException;
import project.asc.AnsimCar.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        AccountCreateRequest accountCreateRequest = createAccountRequest();

        //when
        accountService.register(accountCreateRequest);

        //then
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();
        assertThat(accountCreateRequest.getUsername()).isEqualTo(account.getUsername());
    }

    @Test
    @DisplayName("이메일이 중복이면 회원가입이 불가능하고 예외를 던진다")
    public void 이메일이_중복이면_회원가입이_불가능하고_예외를_던진다() {
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);

        AccountCreateRequest emailDuplicateAccount = AccountCreateRequest.builder()
                .username("Woo")
                .email("account@gmail.com")     //중복 이메일
                .password("5678")
                .phoneNumber("070")
                .age(20)
                .build();

        //when & then
        assertThatThrownBy(() -> accountService.register(emailDuplicateAccount)).isInstanceOf(EmailExistException.class);
    }
}