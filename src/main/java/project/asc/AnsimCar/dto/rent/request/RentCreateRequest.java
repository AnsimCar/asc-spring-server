package project.asc.AnsimCar.dto.rent.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import project.asc.AnsimCar.domain.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
public class RentCreateRequest {

    @NotNull(message = "차량을 선택해주세요.")
    private Long userCarId;
    @NotNull(message = "시간 당 가격을 입력해주세요.")
    @Positive
    private int pricePerHour;
    @NotBlank(message = "시도(예: 서울특별시)를 입력해주세요.")
    private String sido;
    @NotBlank(message = "시군구(예: 강남구)를 입력해주세요.")
    private String sigungu;
    @NotBlank(message = "읍면동(예: 청담동)를 입력해주세요.")
    private String eupmyeondong;
    @NotBlank(message = "상세주소(예: 공용주차장)를 입력해주세요.")
    private String detailAddress;


    public RentCreateRequest() {
    }

    @Builder
    public RentCreateRequest(Long userCarId, int pricePerHour, String sido, String sigungu, String eupmyeondong, String detailAddress) {
        this.userCarId = userCarId;
        this.pricePerHour = pricePerHour;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
        this.detailAddress = detailAddress;
    }

    public Address toAddressEntity() {
        return Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .detailAddress(detailAddress)
                .build();
    }
}
