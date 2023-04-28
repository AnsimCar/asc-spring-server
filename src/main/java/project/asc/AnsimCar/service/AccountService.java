package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountPasswordResetRequest;
import project.asc.AnsimCar.dto.account.request.AccountUpdateRequest;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.exception.account.AccountNotFoundException;
import project.asc.AnsimCar.exception.account.EmailExistException;
import project.asc.AnsimCar.exception.account.PasswordCheckException;
import project.asc.AnsimCar.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse findById(Long accountId) {
        return AccountResponse.from(accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new));
    }

    /**
     * 회원가입
     */
    public void register(AccountCreateRequest accountCreateRequest) {
        //Account로 변환
        Account account = accountCreateRequest.toEntity();

        //email 중복 체크 (DB에 이미 존재하는 이메일이면 회원가입 불가)
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) throw new EmailExistException();

        //password 암호화
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        //저장
        accountRepository.save(account);
    }

    /**
     * 계정 정보 업데이트
     */
    public void update(Account account, AccountUpdateRequest accountUpdateRequest) {
        Account findAccount = accountRepository.findById(account.getId()).orElseThrow(AccountNotFoundException::new);

        findAccount.updateAccount(accountUpdateRequest);
    }

    /**
     * 비밀번호 재설정
     */
    public void passwordReset(Account account, AccountPasswordResetRequest accountPasswordResetRequest) {

        //기존 비밀번호 확인
        //.matches(raw, encoded): 암호화 되지 않은 비밀번호(raw)와 암호화된 비밀번호(encoded) 비교
        if (!passwordEncoder.matches(accountPasswordResetRequest.getCurrentPassword(), account.getPassword())) {
            throw new PasswordCheckException();
        }

        //새 비밀번호 체크
        if (!accountPasswordResetRequest.getNewPassword().equals(accountPasswordResetRequest.getCheckPassword())){
            throw new PasswordCheckException();
        }

        Account findAccount = accountRepository.findById(account.getId()).orElseThrow(AccountNotFoundException::new);

        String checkPassword = passwordEncoder.encode(accountPasswordResetRequest.getCheckPassword());
        findAccount.setPassword(checkPassword);
    }

}
