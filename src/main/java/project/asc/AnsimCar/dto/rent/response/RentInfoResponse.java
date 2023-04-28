package project.asc.AnsimCar.dto.rent.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.address.response.AddressResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;

import java.time.LocalDateTime;

@Data
public class RentInfoResponse {
    private Long id;

    private UserCarResponse userCarResponse;

    private AccountResponse accountResponse;

    private AddressResponse addressResponse;

    private int pricePerHour;

    private int totalPrice;

    private LocalDateTime registrationDate;

    private Status status;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @Builder
    public RentInfoResponse(Long id, UserCarResponse userCarResponse, AccountResponse accountResponse, AddressResponse addressResponse, int pricePerHour, int totalPrice, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.id = id;
        this.userCarResponse = userCarResponse;
        this.accountResponse = accountResponse;
        this.addressResponse = addressResponse;
        this.pricePerHour = pricePerHour;
        this.totalPrice = totalPrice;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    /**
     * 엔티티 -> ResponseDto
     */
    public static RentInfoResponse from(Rent entity) {
        return new RentInfoResponse(
                entity.getId(),
                UserCarResponse.from(entity.getUserCar()),
                AccountResponse.from(entity.getAccount()),
                AddressResponse.from(entity.getAddress()),
                entity.getPricePerHour(),
                entity.getTotalPrice(),
                entity.getRegistrationDate(),
                entity.getStatus(),
                entity.getRentalDate(),
                entity.getReturnDate()
        );
    }
}
