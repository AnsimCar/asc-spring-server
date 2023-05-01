package project.asc.AnsimCar.dto.usercar.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.review.response.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCarResponse {
    private Long id;

    private AccountResponse accountResponse;

    private String carModel;

    private CarCategory carCategory;

    private String manufacturer;

    private Fuel fuel;

    private String carNumber;

    private Boolean usable;

    private List<ReviewResponse> reviewResponses = new ArrayList<>();

    @Builder
    public UserCarResponse(Long id, AccountResponse accountResponse, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, String carNumber, Boolean usable, List<ReviewResponse> reviewResponses) {
        this.id = id;
        this.accountResponse = accountResponse;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.carNumber = carNumber;
        this.usable = usable;
        this.reviewResponses = reviewResponses;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static UserCarResponse from(UserCar entity) {
        return new UserCarResponse(
                entity.getId(),
                AccountResponse.from(entity.getAccount()),
                entity.getCarModel(),
                entity.getCarCategory(),
                entity.getManufacturer(),
                entity.getFuel(),
                entity.getCarNumber(),
                entity.getUsable(),
                ReviewResponse.from(entity.getReviews())
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public UserCar toEntity() {
        return UserCar.builder()
                .account(accountResponse.toEntity())
                .carModel(carModel)
                .carCategory(carCategory)
                .manufacturer(manufacturer)
                .fuel(fuel)
                .carNumber(carNumber)
                .usable(usable)
                .build();
    }
}
