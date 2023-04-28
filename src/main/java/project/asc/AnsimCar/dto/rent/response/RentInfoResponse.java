package project.asc.AnsimCar.dto.rent.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.address.response.AddressResponse;
import project.asc.AnsimCar.dto.review.response.ReviewResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RentInfoResponse {

    private int pricePerHour;

    private Status status;


    private String carModel;

    private CarCategory carCategory;

    private String manufacturer;

    private Fuel fuel;

    private List<ReviewResponse> reviewResponseList = new ArrayList<>();


    private String sido;

    private String sigungu;

    private String eupmyeongdong;


    @QueryProjection
    public RentInfoResponse(int pricePerHour, Status status, String carModel, CarCategory carCategory, String manufacturer, Fuel fuel, List<ReviewResponse> reviewResponseList, String sido, String sigungu, String eupmyeongdong) {
        this.pricePerHour = pricePerHour;
        this.status = status;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.manufacturer = manufacturer;
        this.fuel = fuel;
        this.reviewResponseList = reviewResponseList;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeongdong = eupmyeongdong;
    }
}
