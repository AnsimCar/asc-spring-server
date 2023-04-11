package project.asc.AnsimCar.dto.usercar.request;

import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import javax.validation.constraints.NotBlank;

@Data
public class UserCarCreateRequest {
    @NotBlank(message = "모델명을 입력해주세요.")
    String carModel;

    @NotBlank(message = "차종을 입력해주세요.")
    CarCategory carCategory;

    @NotBlank(message = "제조사를 입력해주세요.")
    String manufacturer;

    @NotBlank(message = "연료를 입력해주세요.")
    Fuel fuel;

    @NotBlank(message = "차량 번호를 입력해주세요.")
    String carNumber;

    private UserCarCreateRequest() {
    }

    public UserCarCreateRequest(final String carModel, final CarCategory carCategory, final String manufacturer, final Fuel fuel, final String carNumber) {
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.carNumber = carNumber;
    }
}
