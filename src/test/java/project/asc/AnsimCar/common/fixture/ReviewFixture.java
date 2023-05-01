package project.asc.AnsimCar.common.fixture;

import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.Review;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.Rate;
import project.asc.AnsimCar.dto.review.request.ReviewCreateRequest;
import project.asc.AnsimCar.dto.review.request.ReviewUpdateRequest;

import java.time.LocalDateTime;

public class ReviewFixture {
    public static Review createReview(UserCar userCar, Rent rent, Account account, Rate rate, String description, LocalDateTime reviewDate) {
        return Review.builder()
                .userCar(userCar)
                .rent(rent)
                .account(account)
                .rate(rate)
                .description(description)
                .reviewDate(reviewDate)
                .build();
    }

    public static ReviewCreateRequest reviewCreateRequest(Rate rate, String description) {
        return new ReviewCreateRequest(
                rate,
                description
        );
    }

    public static ReviewUpdateRequest reviewUpdateRequest(Rate rate, String description) {
        return new ReviewUpdateRequest(
                rate,
                description
        );
    }
}
