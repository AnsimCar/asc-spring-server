package project.asc.AnsimCar.dto.image.before.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.BeforeImage;

@Data
public class BeforeImageResponse {
    private Long id;

    private String imageFront;
    private String imageRear;
    private String imageRight;
    private String imageLeft;

    @Builder
    public BeforeImageResponse(Long id, String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.id = id;
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static BeforeImageResponse from(BeforeImage entity) {
        return new BeforeImageResponse(
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
    public BeforeImage toEntity() {
        return BeforeImage.builder()
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();
    }
}
