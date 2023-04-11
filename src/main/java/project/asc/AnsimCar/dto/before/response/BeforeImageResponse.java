package project.asc.AnsimCar.dto.before.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.BeforeImage;

@Data
public class BeforeImageResponse {
    private Long id;

    private String imageUrl;

    @Builder
    public BeforeImageResponse(Long id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static BeforeImageResponse from(BeforeImage entity) {
        return new BeforeImageResponse(
                entity.getId(),
                entity.getImageUrl()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public BeforeImage toEntity() {
        return BeforeImage.builder()
                .imageUrl(imageUrl)
                .build();
    }
}
