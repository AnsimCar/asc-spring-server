package project.asc.AnsimCar.dto.address.request;

import lombok.Data;
import project.asc.AnsimCar.domain.Address;

import javax.validation.constraints.NotBlank;

@Data
public class AddressCreateRequest {
    @NotBlank(message = "이미지 주소를 입력하세요.")
    String imageUrl;

    @NotBlank(message = "시도를 입력하세요.")
    String sido;

    @NotBlank(message = "시군구를 입력하세요.")
    String sigungu;

    @NotBlank(message = "읍면동을 입력하세요.")
    String eupmyeondong;

    private AddressCreateRequest() {
    }

    public AddressCreateRequest(String sido, String sigungu, String eupmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
    }

    public Address toEntity() {
        return new Address(sido, sigungu, eupmyeondong);
    }
}
