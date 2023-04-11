package project.asc.AnsimCar.dto.image.after.request;

import lombok.Data;
import project.asc.AnsimCar.domain.AfterImage;

import javax.validation.constraints.NotBlank;

@Data
public class AfterImageCreateRequest {
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageUrl;

    private AfterImageCreateRequest() {
    }

    public AfterImageCreateRequest(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public AfterImage toEntity() {
        return new AfterImage(imageUrl);
    }
}
