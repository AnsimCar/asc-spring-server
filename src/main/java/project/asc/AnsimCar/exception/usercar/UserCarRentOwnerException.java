package project.asc.AnsimCar.exception.usercar;

public class UserCarRentOwnerException extends UserCarException {
    private static final String MESSAGE = "대여자가 아닙니다.";

    public UserCarRentOwnerException() {
        super(MESSAGE);
    }
}