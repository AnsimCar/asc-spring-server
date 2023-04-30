package project.asc.AnsimCar.dto.review.request;

import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReviewCreateRequest {
    @NotNull(message = "차량을 입력해주세요.")
    Long userCarId;

    @NotNull(message = "카셰어링 내역을 입력해주세요.")
    Long rentId;

    @NotNull(message = "리뷰어를 입력하세요.")
    Long accountId;

    @NotBlank(message = "별점을 입력하세요.")
    int rate;

    String description;

    @NotBlank(message = "리뷰 날짜를 입력하세요.")
    LocalDateTime reviewDate;

    private ReviewCreateRequest() {
    }

    public ReviewCreateRequest(final Long userCarId, final Long rent, final Long account, final int rate, final String description, final LocalDateTime reviewDate) {
        this.userCarId = userCarId;
        this.rentId = rentId;
        this.accountId = accountId;
        this.rate = rate;
        this.description = description;
        this.reviewDate = reviewDate;
    }
}
