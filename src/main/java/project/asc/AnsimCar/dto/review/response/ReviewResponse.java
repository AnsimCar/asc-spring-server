package project.asc.AnsimCar.dto.review.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.Review;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewResponse {
    private Long id;

    private UserCar userCar;

    private Rent rent;

    private Account account;

    private int rate;

    private String description;

    private LocalDateTime reviewDate;

    @Builder
    public ReviewResponse(Long id, UserCar userCar, Rent rent, Account account, int rate, String description, LocalDateTime reviewDate) {
        this.id = id;
        this.userCar = userCar;
        this.rent = rent;
        this.account = account;
        this.rate = rate;
        this.description = description;
        this.reviewDate = reviewDate;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static ReviewResponse from(Review entity) {
        return new ReviewResponse(
                entity.getId(),
                entity.getUserCar(),
                entity.getRent(),
                entity.getAccount(),
                entity.getRate(),
                entity.getDescription(),
                entity.getReviewDate()
        );
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static List<ReviewResponse> from(List<Review> entities) {
        List<ReviewResponse> reviewResponses = new ArrayList<>();

        for (Review entity : entities) {
            reviewResponses.add(new ReviewResponse(
                    entity.getId(),
                    entity.getUserCar(),
                    entity.getRent(),
                    entity.getAccount(),
                    entity.getRate(),
                    entity.getDescription(),
                    entity.getReviewDate()
            ));
        }

        return reviewResponses;
    }

    /**
     * RequestDto -> 엔티티
     */
    public Review toEntity() {
        return Review.builder()
                .userCar(userCar)
                .rent(rent)
                .account(account)
                .rate(rate)
                .description(description)
                .reviewDate(reviewDate)
                .build();
    }
}
