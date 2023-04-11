package project.asc.AnsimCar.exception;

public abstract class UserCarException extends RuntimeException {

    public UserCarException(String msg) {
        super(msg);
    }
}