package project.asc.AnsimCar.dto.image.returnImage.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.AfterImage;

@Data
public class ReturnImageResponse {
    private Long id;

    private String imageFront;
    private String imageRear;
    private String imageRight;
    private String imageLeft;

    @Builder
    public ReturnImageResponse(Long id, String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.id = id;
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static ReturnImageResponse from(AfterImage entity) {
        return new ReturnImageResponse(
                entity.getId(),
                entity.getImageFront(),
                entity.getImageRear(),
                entity.getImageRight(),
                entity.getImageLeft()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public AfterImage toEntity() {
        return AfterImage.builder()
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();
    }
}
