package project.asc.AnsimCar.domain;

import lombok.Getter;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import javax.persistence.*;

@Getter
@Entity
public class UserCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_car_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private String carModel;    //차종

    @Enumerated(EnumType.STRING)
    private CarCategory carCategory;    //분류

    private String manufacturer;        //제조사

    @Enumerated(EnumType.STRING)
    private Fuel fuel;                  //연료 종류

    private String carNumber;           //차량 번호

}
