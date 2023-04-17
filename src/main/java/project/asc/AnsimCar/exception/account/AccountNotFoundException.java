package project.asc.AnsimCar.exception.account;

public class AccountNotFoundException extends AccountException {
    private static final String MESSAGE = "유저 검색 결과가 없습니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
