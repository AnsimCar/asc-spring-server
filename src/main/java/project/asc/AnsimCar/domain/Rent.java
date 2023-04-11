package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinColumn(name = "before_image_id")
    private List<BeforeImage> beforeImages;

    @OneToMany
    @JoinColumn(name = "after_image_id")
    private List<AfterImage> afterImages;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime rentalDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime returnDate;

    protected Rent() {
    }

    @Builder
    public Rent(UserCar userCar, Address address, List<BeforeImage> beforeImages, List<AfterImage> afterImages, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.userCar = userCar;
        this.address = address;
        this.beforeImages = beforeImages;
        this.afterImages = afterImages;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public void updateStatus(RentUpdateRequest rentUpdateRequest) {
        this.status = rentUpdateRequest.getStatus();
    }

    public void updateRentalDate(RentUpdateRequest rentUpdateRequest) {
        this.rentalDate = rentUpdateRequest.getRentalDate();
    }

    public void updateReturnDate(RentUpdateRequest rentUpdateRequest) {
        this.returnDate = rentUpdateRequest.getReturnDate();
    }
}
