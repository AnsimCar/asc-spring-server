package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_car_id")
    private UserCar userCar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account; //리뷰어 계정

    private int rate;

    private String description;

    private LocalDateTime reviewDate;

    protected Review() {
    }

    @Builder
    public Review(UserCar userCar, Rent rent, Account account, int rate, String description, LocalDateTime reviewDate) {
        this.userCar = userCar;
        this.rent = rent;
        this.account = account;
        this.rate = rate;
        this.description = description;
        this.reviewDate = reviewDate;
    }
}
