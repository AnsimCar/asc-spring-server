package project.asc.AnsimCar.dto.review.request;

import lombok.Data;
import project.asc.AnsimCar.domain.type.Rate;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewUpdateRequest {
    @NotBlank(message = "별점을 입력하세요.")
    Rate rate;

    String description;

    private ReviewUpdateRequest() {
    }

    public ReviewUpdateRequest(final Rate rate, final String description) {
        this.rate = rate;
        this.description = description;
    }
}
