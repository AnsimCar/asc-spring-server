package project.asc.AnsimCar.dto.usercar.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.account.AccountDto;

@Data
public class UserCarResponse {
    private Long id;

    private AccountDto accountDto;

    private String carModel;

    private CarCategory carCategory;

    private String manufacturer;

    private Fuel fuel;

    private String carNumber;

    @Builder
    public UserCarResponse(Long id, AccountDto accountDto, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber) {
        this.id = id;
        this.accountDto = accountDto;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.carNumber = carNumber;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static UserCarResponse from(UserCar entity) {
        return new UserCarResponse(
                entity.getId(),
                AccountDto.from(entity.getAccount()),
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
                accountDto.toEntity(),
                carModel,
                carCategory,
                manufacturer,
                fuel,
                carNumber
        );
    }
}
