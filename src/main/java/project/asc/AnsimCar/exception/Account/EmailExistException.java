package project.asc.AnsimCar.exception.Account;

public class EmailExistException extends RuntimeException{

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public EmailExistException() {
        super(MESSAGE);
    }
}
