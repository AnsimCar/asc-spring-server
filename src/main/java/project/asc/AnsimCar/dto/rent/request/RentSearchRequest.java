package project.asc.AnsimCar.dto.rent.request;

import lombok.Data;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;

@Data
public class RentSearchRequest {
    private String carModel;
    private CarCategory carCategory;
    private Fuel fuel;

    private String sido;
    private String sigungu;
    private String eupmyeondong;
}
