package project.asc.AnsimCar.exception.account;

public class PasswordCheckException extends AccountException{

    private static final String MESSAGE = "입력하신 비밀번호를 다시 확인해주세요.";

    public PasswordCheckException() {
        super(MESSAGE);
    }
}
