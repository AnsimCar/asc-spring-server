package project.asc.AnsimCar.exception.review;

public class ReviewOwnerException extends ReviewException {
    private static final String MESSAGE = "해당 리뷰의 작성자가 아닙니다.";

    public ReviewOwnerException() {
        super(MESSAGE);
    }
}
