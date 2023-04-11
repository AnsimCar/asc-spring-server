package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.exception.EmailExistException;
import project.asc.AnsimCar.repository.AccountRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

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
}
