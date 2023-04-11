package project.asc.AnsimCar.exception.Account;

import project.asc.AnsimCar.exception.Account.AccountException;

public class AccountNotFoundException extends AccountException {
    private static final String MESSAGE = "유저 검색 결과가 없습니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
