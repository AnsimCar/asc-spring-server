package project.asc.AnsimCar.dto.rent.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RentResponse {
    private Long id;

    private UserCar userCar;

    private Address address;

    private List<BeforeImage> beforeImages;

    private List<AfterImage> afterImages;

    private LocalDateTime registrationDate;

    private Status status;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @Builder
    public RentResponse(Long id, UserCar userCar, Address address, List<BeforeImage> beforeImages, List<AfterImage> afterImages, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.id = id;
        this.userCar = userCar;
        this.address = address;
        this.beforeImages = beforeImages;
        this.afterImages = afterImages;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static RentResponse from(Rent entity) {
        return new RentResponse(
                entity.getId(),
                entity.getUserCar(),
                entity.getAddress(),
                entity.getBeforeImages(),
                entity.getAfterImages(),
                entity.getRegistrationDate(),
                entity.getStatus(),
                entity.getRentalDate(),
                entity.getReturnDate()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public Rent toEntity() {
        return Rent.builder()
                .userCar(userCar)
                .address(address)
                .beforeImages(beforeImages)
                .afterImages(afterImages)
                .registrationDate(registrationDate)
                .status(status)
                .rentalDate(rentalDate)
                .returnDate(returnDate)
                .build();
    }
}
