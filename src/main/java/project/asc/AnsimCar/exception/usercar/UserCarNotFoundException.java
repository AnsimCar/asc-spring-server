package project.asc.AnsimCar.exception.usercar;

public class UserCarNotFoundException extends UserCarException {
    private static final String MESSAGE = "차량 검색 결과가 없습니다.";

    public UserCarNotFoundException() {
        super(MESSAGE);
    }
}
