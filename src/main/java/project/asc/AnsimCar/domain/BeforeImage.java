package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BeforeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "before_image_id")
    private Long id;

    private String imageUrl;

    protected BeforeImage() {
    }

    @Builder
    public BeforeImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
