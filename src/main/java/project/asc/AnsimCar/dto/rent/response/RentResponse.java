package project.asc.AnsimCar.dto.rent.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.address.response.AddressResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
public class RentResponse {
    private Long id;

    private UserCarResponse userCarResponse;

    private AccountResponse accountResponse;

    private AccountResponse rentalAccountResponse;

    private AddressResponse addressResponse;

    private int pricePerHour;

    private int totalPrice;

    private LocalDateTime registrationDate;

    private Status status;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    @Builder
    public RentResponse(Long id, UserCarResponse userCarResponse, AccountResponse accountResponse, AccountResponse rentalAccountResponse, AddressResponse addressResponse, int pricePerHour, int totalPrice, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.id = id;
        this.userCarResponse = userCarResponse;
        this.accountResponse = accountResponse;
        this.rentalAccountResponse = rentalAccountResponse;
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
    public static RentResponse from(Rent entity) {
        return new RentResponse(
                entity.getId(),
                UserCarResponse.from(entity.getUserCar()),
                AccountResponse.from(entity.getAccount()),
                AccountResponse.from(entity.getRentAccount()),
                AddressResponse.from(entity.getAddress()),
                entity.getPricePerHour(),
                entity.getTotalPrice(),
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
                .userCar(userCarResponse.toEntity())
                .account(accountResponse.toEntity())
                .rentAccount(rentalAccountResponse.toEntity())
                .address(addressResponse.toEntity())
                .pricePerHour(pricePerHour)
                .totalPrice(totalPrice)
                .registrationDate(registrationDate)
                .status(status)
                .rentalDate(rentalDate)
                .returnDate(returnDate)
                .build();
    }

    /**
     * 렌트 주인 확인
     */
    public boolean isOwner(Long accountId) {
        if (accountId == null) {
            return false;
        }
        return accountResponse.getId().equals(accountId);
    }
}
