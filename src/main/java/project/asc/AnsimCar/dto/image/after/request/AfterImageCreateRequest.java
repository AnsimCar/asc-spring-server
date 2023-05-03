package project.asc.AnsimCar.dto.image.after.request;

import lombok.Data;
import project.asc.AnsimCar.domain.AfterImage;
import project.asc.AnsimCar.domain.Rent;

import javax.validation.constraints.NotBlank;

@Data
public class AfterImageCreateRequest {
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageFront;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageRear;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageRight;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageLeft;

    private AfterImageCreateRequest() {
    }

    public AfterImageCreateRequest(String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    public AfterImage toEntity() {
        return new AfterImage(imageFront, imageRear, imageRight, imageLeft);
    }
}
