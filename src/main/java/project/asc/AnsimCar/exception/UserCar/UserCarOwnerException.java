package project.asc.AnsimCar.exception.UserCar;

public class UserCarOwnerException extends UserCarException {
    private static final String MESSAGE = "차주가 아닙니다.";

    public UserCarOwnerException() {
        super(MESSAGE);
    }
}