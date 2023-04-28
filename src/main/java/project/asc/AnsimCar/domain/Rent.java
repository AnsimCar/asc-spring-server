package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;

import javax.persistence.*;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    private Address address;

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

    protected Rent() {
    }

    @Builder
    public Rent(UserCar userCar, Account account, Address address, LocalDateTime registrationDate, Status status, LocalDateTime rentalDate, LocalDateTime returnDate) {
        this.userCar = userCar;
        this.account = account;
        this.address = address;
        this.registrationDate = registrationDate;
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public void updateStatus(RentUpdateRequest rentUpdateRequest) {
        this.status = rentUpdateRequest.getStatus();
    }

    public void updateRentalReturnDate(RentUpdateRequest rentUpdateRequest) {
        this.rentalDate = rentUpdateRequest.getRentalDate();
        this.returnDate = rentUpdateRequest.getReturnDate();
    }



    public boolean isOwner(Long accountId) {
        if (accountId == null) {
            return false;
        }
        return account.getId().equals(accountId);
    }
}
