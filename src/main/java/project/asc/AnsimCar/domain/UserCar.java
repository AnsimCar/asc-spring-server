package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;
import project.asc.AnsimCar.domain.common.BaseEntity;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class UserCar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String carModel;    //모델명

    @Enumerated(EnumType.STRING)
    private CarCategory carCategory;    //차종

    private String manufacturer;        //제조사

    @Enumerated(EnumType.STRING)
    private Fuel fuel;                  //연료 종류

    private String carNumber;           //차량 번호

    private Boolean usable;

    @OneToMany(mappedBy = "userCar", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "userCar")
    private List<Rent> rents = new ArrayList<>();

    protected UserCar() {
    }

    @Builder
    public UserCar(Account account, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber, Boolean usable) {
        this.account = account;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.carNumber = carNumber;
        this.usable = usable;
    }

    public void updateUserCar(UserCarUpdateRequest userCarUpdateRequest) {
        this.carModel = userCarUpdateRequest.getCarModel();
        this.carCategory = userCarUpdateRequest.getCarCategory();
        this.manufacturer = userCarUpdateRequest.getManufacturer();
        this.fuel = userCarUpdateRequest.getFuel();
    }

    public void updateUsable(Boolean usable) {
        this.usable = usable;
    }

    //차량 Owner 검증
    public boolean isOwner(Long accountId) {
        if (accountId == null) {
            return false;
        }
        return account.getId().equals(accountId);
    }
}
