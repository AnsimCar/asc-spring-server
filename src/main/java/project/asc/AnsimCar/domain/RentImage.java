package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class RentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id")
    private Rent rent;

    private String imageFront;
    private String imageRear;
    private String imageRight;
    private String imageLeft;

    protected RentImage() {
    }

    @Builder
    public RentImage(Rent rent, String imageFront, String imageRear, String imageRight, String imageLeft) {
        this.rent = rent;
        this.imageFront = imageFront;
        this.imageRear = imageRear;
        this.imageRight = imageRight;
        this.imageLeft = imageLeft;
    }
}
