package project.asc.AnsimCar.dto.image.after.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.AfterImage;

@Data
public class AfterImageResponse {
    private Long id;

    private String imageFront;
    private String imageRear;
    private String imageRight;
    private String imageLeft;

    @Builder
    public AfterImageResponse(Long id, String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.id = id;
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static AfterImageResponse from(AfterImage entity) {
        return new AfterImageResponse(
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
