package project.asc.AnsimCar.exception.Account;

public abstract class AccountException extends RuntimeException {
    public AccountException(String msg) {
        super(msg);
    }
}