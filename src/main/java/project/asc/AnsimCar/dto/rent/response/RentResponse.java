package project.asc.AnsimCar.dto.rent.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.address.response.AddressResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RentResponse {
    private Long id;

    private UserCar userCar;

    private AddressResponse addressResponse;

    private LocalDateTime registrationDate;

    private Status status;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @Builder
    public RentResponse(Long id, UserCar userCar, AddressResponse addressResponse, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.id = id;
        this.userCar = userCar;
        this.addressResponse = addressResponse;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    /**
     * 엔티티 -> ResponseDto
     */
    public static RentResponse from(Rent entity) {
        return new RentResponse(
                entity.getId(),
                entity.getUserCar(),
                AddressResponse.from(entity.getAddress()),
                entity.getRegistrationDate(),
                entity.getStatus(),
                entity.getRentalDate(),
                entity.getReturnDate()
        );
    }

    /**
     * ResponseDto -> 엔티티
     */
    public Rent toEntity() {
        return Rent.builder()
                .userCar(userCar)
                .address(addressResponse.toEntity())
                .registrationDate(registrationDate)
                .status(status)
                .rentalDate(rentalDate)
                .returnDate(returnDate)
                .build();
    }
}
