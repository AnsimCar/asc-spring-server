package project.asc.AnsimCar.dto.image.rentImage.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.RentImage;

@Data
public class RentImageResponse {
    private Long id;

    private String imageFront;
    private String imageRear;
    private String imageRight;
    private String imageLeft;

    @Builder
    public RentImageResponse(Long id, String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.id = id;
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static RentImageResponse from(RentImage entity) {
        return new RentImageResponse(
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
    public RentImage toEntity() {
        return RentImage.builder()
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();
    }
}
