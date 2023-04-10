package project.asc.AnsimCar.domain;

import lombok.Getter;
import project.asc.AnsimCar.domain.common.BaseEntity;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import javax.persistence.*;

@Getter
@Entity
public class UserCar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_car_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String carModel;    //모델명

    @Enumerated(EnumType.STRING)
    private CarCategory carCategory;    //차종

    private String manufacturer;        //제조사

    @Enumerated(EnumType.STRING)
    private Fuel fuel;                  //연료 종류

    private String carNumber;           //차량 번호

    protected UserCar() {
    }

    private UserCar(Account account, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber) {
        this.account = account;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.carNumber = carNumber;
    }

    public UserCar setUserCar(UserCar userCar) {
        this.account = userCar.getAccount();
        this.carModel = userCar.getCarModel();
        this.carCategory = userCar.getCarCategory();
        this.manufacturer = userCar.getManufacturer();
        this.fuel = userCar.getFuel();
        this.carNumber = userCar.getCarNumber();

        return this;
    }

    /**
     * 팩토리 복원
     */
    public static UserCar of(Account account, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber) {
        return new UserCar(account, carModel, carCategory, manufacturer, fuel, carNumber);
    }
}
