package project.asc.AnsimCar.dto.image.returnImage.request;

import lombok.Data;
import project.asc.AnsimCar.domain.ReturnImage;

import javax.validation.constraints.NotBlank;

@Data
public class ReturnImageCreateRequest {
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageFront;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageRear;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageRight;
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageLeft;

    private ReturnImageCreateRequest() {
    }

    public ReturnImageCreateRequest(String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    public ReturnImage toEntity() {
        return new ReturnImage(imageFront, imageRear, imageRight, imageLeft);
    }
}
