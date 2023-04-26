package project.asc.AnsimCar.dto.rent.request;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class RentCreateRequest {

    @NotNull(message = "차량을 선택해주세요.")
    private Long userCarId;
    @NotBlank(message = "시도(예: 서울특별시)를 입력해주세요.")
    private String sido;
    @NotBlank(message = "시군구(예: 강남구)를 입력해주세요.")
    private String sigungu;
    @NotBlank(message = "읍면동(예: 청담동)를 입력해주세요.")
    private String eupmyeondong;


    public RentCreateRequest() {
    }

    @Builder
    public RentCreateRequest(Long userCarId, String sido, String sigungu, String eupmyeondong) {
        this.userCarId = userCarId;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
    }

    public Address toAddressEntity() {
        return Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeondong)
                .build();
    }
}
