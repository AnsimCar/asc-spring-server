package project.asc.AnsimCar.dto.usercar.request;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import javax.validation.constraints.NotBlank;

@Data
public class UserCarUpdateRequest {
    @NotBlank(message = "모델명을 입력해주세요.")
    String carModel;

//    @NotBlank(message = "차종을 입력해주세요.")
    CarCategory carCategory;

    @NotBlank(message = "제조사를 입력해주세요.")
    String manufacturer;

//    @NotBlank(message = "연료를 입력해주세요.")
    Fuel fuel;

    private UserCarUpdateRequest() {
    }

    @Builder
    public UserCarUpdateRequest(final String carModel, final CarCategory carCategory, final String manufacturer, final Fuel fuel) {
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
    }
}

