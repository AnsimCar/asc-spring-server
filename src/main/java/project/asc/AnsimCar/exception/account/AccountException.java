package project.asc.AnsimCar.exception.account;

public abstract class AccountException extends RuntimeException {
    public AccountException(String msg) {
        super(msg);
    }
}