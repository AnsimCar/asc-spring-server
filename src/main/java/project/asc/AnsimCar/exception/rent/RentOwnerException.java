package project.asc.AnsimCar.exception.rent;

public class RentOwnerException extends RentException{

    private static final String MESSAGE = "유저의 카셰어링 내역이 아닙니다.";

    public RentOwnerException() {
        super(MESSAGE);
    }

}
