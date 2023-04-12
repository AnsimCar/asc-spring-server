package project.asc.AnsimCar.exception.rent;

public class RentOwnerException extends RentException{

    private static final String MESSAGE = "자신의 렌트만 수정 할 수 있습니다.";

    public RentOwnerException() {
        super(MESSAGE);
    }

}
