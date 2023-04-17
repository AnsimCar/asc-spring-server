package project.asc.AnsimCar.exception.review;

public class ReviewNotFoundException extends ReviewException {
    private static final String MESSAGE = "리뷰 검색 결과가 없습니다.";

    public ReviewNotFoundException() {
        super(MESSAGE);
    }
}
