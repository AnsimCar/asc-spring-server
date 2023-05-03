package project.asc.AnsimCar.exception.rent;

public class NotRentingAndWaitingReturnException extends RentException{

    private static final String MESSAGE = "카쉐어링 중이거나 반납 대기 중이 아닙니다.";

    public NotRentingAndWaitingReturnException() {
        super(MESSAGE);
    }
}
