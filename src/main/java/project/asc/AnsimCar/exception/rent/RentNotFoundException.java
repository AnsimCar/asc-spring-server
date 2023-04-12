package project.asc.AnsimCar.exception.rent;

public class RentNotFoundException extends RentException{

    private static final String MESSAGE = "렌트 검색 결과가 없습니다.";

    public RentNotFoundException() {
        super(MESSAGE);
    }
}
