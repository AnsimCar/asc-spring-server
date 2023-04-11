package project.asc.AnsimCar.dto.rent.request;

import lombok.Data;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Status;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RentCreateRequest {
    @NotBlank(message = "등록 날짜를 입력하세요.")
    LocalDateTime registrationDate;

    @NotBlank(message = "상태를 입력하세요.")
    Status status;

    private RentCreateRequest() {
    }

    public RentCreateRequest(final String LocalDateTime, final Status status) {
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Rent toEntity(final UserCar userCar, final Address address) {
        return new Rent(userCar, address, registrationDate, status, null, null);
    }
}
