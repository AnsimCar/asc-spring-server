package project.asc.AnsimCar.dto.review.request;

import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ReviewCreateRequest {
    @NotBlank(message = "차량을 입력해주세요.")
    UserCar userCar;

    @NotBlank(message = "렌트 내역을 입력해주세요.")
    Rent rent;

    @NotBlank(message = "리뷰어를 입력하세요.")
    Account account;

    @NotBlank(message = "별점을 입력하세요.")
    int rate;

    String description;

    @NotBlank(message = "리뷰 날짜를 입력하세요.")
    LocalDateTime reviewDate;

    private ReviewCreateRequest() {
    }

    public ReviewCreateRequest(final UserCar userCar, final Rent rent, final Account account, final int rate, final String description, final LocalDateTime reviewDate) {
        this.userCar = userCar;
        this.rent = rent;
        this.account = account;
        this.rate = rate;
        this.description = description;
        this.reviewDate = reviewDate;
    }
}
