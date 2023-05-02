package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_account_id")
    private Account rentAccount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    private Address address;

    private int pricePerHour;

    private int totalPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime rentalDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime returnDate;

    @OneToMany(mappedBy = "rent", cascade = CascadeType.REMOVE)
    private List<BeforeImage> beforeImages = new ArrayList<>();

    @OneToMany(mappedBy = "rent", cascade = CascadeType.REMOVE)
    private List<AfterImage> afterImages = new ArrayList<>();

    @OneToOne(mappedBy = "rent", cascade = CascadeType.REMOVE)
    private Review review;

    protected Rent() {
    }

    @Builder
    public Rent(UserCar userCar, Account account, Account rentAccount, Address address, int pricePerHour, int totalPrice, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate, Review review) {
        this.userCar = userCar;
        this.account = account;
        this.rentAccount = rentAccount;
        this.address = address;
        this.pricePerHour = pricePerHour;
        this.totalPrice = totalPrice;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.review = review;
    }

    public void updateStatus(RentUpdateRequest rentUpdateRequest) {
        this.status = rentUpdateRequest.getStatus();
    }

    public void updateRentAccount(Account rentAccount) {
        this.rentAccount = rentAccount;
    }

    public void updateRentalReturnDate(RentUpdateRequest rentUpdateRequest) {
        this.status = rentUpdateRequest.getStatus();
        this.rentalDate = rentUpdateRequest.getRentalDate();
        this.returnDate = rentUpdateRequest.getReturnDate();

        if (rentUpdateRequest.getReturnDate() != null) {
            int totalSeconds = (int) Duration.between(rentUpdateRequest.getRentalDate(), rentUpdateRequest.getReturnDate()).getSeconds();

            int totalHour = (totalSeconds / 60 / 60) + 1;

            this.totalPrice = totalHour * pricePerHour;
        }
    }


    public boolean isOwner(Long accountId) {
        if (accountId == null) {
            return false;
        }
        return account.getId().equals(accountId);
    }

    public boolean isRentOwner(Long accountId) {
        if (accountId == null) {
            return false;
        }
        return rentAccount.getId().equals(accountId);
    }
}
