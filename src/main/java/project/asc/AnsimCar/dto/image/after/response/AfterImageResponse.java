package project.asc.AnsimCar.dto.image.after.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.AfterImage;

@Data
public class AfterImageResponse {
    private Long id;

    private String imageUrl;

    @Builder
    public AfterImageResponse(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static AfterImageResponse from(AfterImage entity) {
        return new AfterImageResponse(
                entity.getId(),
                entity.getImageUrl()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public AfterImage toEntity() {
        return AfterImage.builder()
                .imageUrl(imageUrl)
                .build();
    }
}
