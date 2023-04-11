package project.asc.AnsimCar.dto.before.request;

import lombok.Data;
import project.asc.AnsimCar.domain.BeforeImage;

import javax.validation.constraints.NotBlank;

@Data
public class BeforeImageCreateRequest {
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageUrl;

    private BeforeImageCreateRequest() {
    }

    public BeforeImageCreateRequest(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BeforeImage toEntity() {
        return new BeforeImage(imageUrl);
    }
}
