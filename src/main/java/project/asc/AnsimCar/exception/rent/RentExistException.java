package project.asc.AnsimCar.exception.rent;

public class RentExistException extends RentException{

    private static final String MESSAGE = "해당 차량은 이미 등록되어 있습니다.";

    public RentExistException() {
        super(MESSAGE);
    }
}
