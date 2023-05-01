package project.asc.AnsimCar.dto.review.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Review;
import project.asc.AnsimCar.domain.type.Rate;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewResponse {
    private Long id;

    private UserCarResponse userCarResponse;

    private RentResponse rentResponse;

    private AccountResponse accountResponse;

    private int rate;

    private String description;

    private LocalDateTime reviewDate;

    @Builder
    public ReviewResponse(Long id, UserCarResponse userCarResponse, RentResponse rentResponse, AccountResponse accountResponse, int rate, String description, LocalDateTime reviewDate) {
        this.id = id;
        this.userCarResponse = userCarResponse;
        this.rentResponse = rentResponse;
        this.accountResponse = accountResponse;
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
                UserCarResponse.from(entity.getUserCar()),
                RentResponse.from(entity.getRent()),
                AccountResponse.from(entity.getAccount()),
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
            reviewResponses.add(ReviewResponse.builder()
                    .id(entity.getId())
                    .userCarResponse(UserCarResponse.from(entity.getUserCar()))
                    .rentResponse(RentResponse.from(entity.getRent()))
                    .accountResponse(AccountResponse.from(entity.getAccount()))
                    .rate(entity.getRate())
                    .description(entity.getDescription())
                    .reviewDate(entity.getReviewDate())
                    .build());
        }

        return reviewResponses;
    }

    /**
     * RequestDto -> 엔티티
     */
    public Review toEntity() {
        return Review.builder()
                .userCar(userCarResponse.toEntity())
                .rent(rentResponse.toEntity())
                .account(accountResponse.toEntity())
                .rate(rate)
                .description(description)
                .reviewDate(reviewDate)
                .build();
    }
}
