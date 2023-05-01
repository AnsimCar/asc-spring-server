package project.asc.AnsimCar.dto.review.request;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.Rate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ReviewCreateRequest {
    @NotNull(message = "별점을 입력하세요.")
    Rate rate;

    String description;

    public ReviewCreateRequest() {
    }

    @Builder
    public ReviewCreateRequest(final Rate rate, final String descriptione) {
        this.rate = rate;
        this.description = description;
    }
}
