package project.asc.AnsimCar.dto.usercar;

import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

import javax.validation.constraints.NotBlank;

public record UserCarRequest(
        @NotBlank(message = "유저를 입력해주세요.")
        Account account,

        @NotBlank(message = "모델명을 입력해주세요.")
        String carModel,

        @NotBlank(message = "차종을 입력해주세요.")
        CarCategory carCategory,

        @NotBlank(message = "제조사를 입력해주세요.")
        String manufacturer,

        @NotBlank(message = "연료 종류를 입력해주세요.")
        Fuel fuel,

        @NotBlank(message = "차량 번호를 입력해주세요.")
        String carNumber
) {
    public static UserCarRequest of(Account account, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber) {
        return new UserCarRequest(account, carModel, carCategory, manufacturer, fuel, carNumber);
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static UserCarRequest from(UserCar entity) {
        return new UserCarRequest(
                entity.getAccount(),
                entity.getCarModel(),
                entity.getCarCategory(),
                entity.getManufacturer(),
                entity.getFuel(),
                entity.getCarNumber()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public UserCar toEntity() {
        return UserCar.of(
                account,
                carModel,
                carCategory,
                manufacturer,
                fuel,
                carNumber
        );
    }
}
