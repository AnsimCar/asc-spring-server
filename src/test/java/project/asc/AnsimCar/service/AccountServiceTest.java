package project.asc.AnsimCar.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.domain.Account;

import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountPasswordResetRequest;
import project.asc.AnsimCar.dto.account.request.AccountUpdateRequest;
import project.asc.AnsimCar.exception.account.EmailExistException;
import project.asc.AnsimCar.exception.account.PasswordCheckException;
import project.asc.AnsimCar.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static project.asc.AnsimCar.common.fixture.AccountFixture.*;


class AccountServiceTest extends ServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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

    @Test
    @DisplayName("계정 정보 업데이트")
    public void 계정_정보_업데이트(){
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);

        //실제 로직에선 세션에서 account를 가져온다.
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        //when
        AccountUpdateRequest accountUpdateRequest = createAccountUpdateRequest();
        accountService.update(account, accountUpdateRequest);

        //then
        Account updateAccount = accountRepository.findByEmail(accountUpdateRequest.getEmail()).get();
        Assertions.assertThat(updateAccount.getUsername()).isEqualTo(accountUpdateRequest.getUsername());
        Assertions.assertThat(updateAccount.getEmail()).isEqualTo(accountUpdateRequest.getEmail());
        Assertions.assertThat(updateAccount.getPhoneNumber()).isEqualTo(accountUpdateRequest.getPhoneNumber());
        Assertions.assertThat(updateAccount.getAge()).isEqualTo(accountUpdateRequest.getAge());
    }


    @Test
    @DisplayName("비밀번호 재설정")
    public void 비밀번호_재설정(){
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);

        //실제 로직에선 세션에서 account를 가져온다.
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        //when
        AccountPasswordResetRequest accountPasswordResetRequestSuccess = createAccountPasswordResetRequestSuccess();
        accountService.passwordReset(account, accountPasswordResetRequestSuccess);

        //then
        Account passwordResetAccount = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();
        boolean matches = passwordEncoder.matches(accountPasswordResetRequestSuccess.getCheckPassword(), passwordResetAccount.getPassword());
        assertTrue(matches);
    }

    @Test
    @DisplayName("기존 비밀번호와 일치하지 않으면 예외를 던진다")
    public void 기존_비밀번호와_일치하지_않으면_예외를_던진다(){
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);

        //실제 로직에선 세션에서 account를 가져온다.
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        //when & then
        AccountPasswordResetRequest accountPasswordResetRequestSuccess = createAccountPasswordResetRequestFail1();
        assertThatThrownBy(() -> accountService.passwordReset(account, accountPasswordResetRequestSuccess)).isInstanceOf(PasswordCheckException.class);

    }

    @Test
    @DisplayName("새로운 비밀번호를 입력한 두 개의 값이 일치하지 않으면 예외를 던진다")
    public void 새로운_비밀번호를_입력한_두_개의_값이_일치하지_않으면_예외를_던진다(){
        //given
        AccountCreateRequest accountCreateRequest = createAccountRequest();
        accountService.register(accountCreateRequest);

        //실제 로직에선 세션에서 account를 가져온다.
        Account account = accountRepository.findByEmail(accountCreateRequest.getEmail()).get();

        //when & then
        AccountPasswordResetRequest accountPasswordResetRequestSuccess = createAccountPasswordResetRequestFail2();
        assertThatThrownBy(() -> accountService.passwordReset(account, accountPasswordResetRequestSuccess)).isInstanceOf(PasswordCheckException.class);

    }
}