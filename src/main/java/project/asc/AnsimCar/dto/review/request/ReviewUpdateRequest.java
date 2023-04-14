package project.asc.AnsimCar.dto.review.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewUpdateRequest {
    @NotBlank(message = "별점을 입력하세요.")
    int rate;

    String description;

    private ReviewUpdateRequest() {
    }

    public ReviewUpdateRequest(final int rate, final String description) {
        this.rate = rate;
        this.description = description;
    }
}
