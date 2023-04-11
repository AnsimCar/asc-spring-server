package project.asc.AnsimCar.dto.rent.request;

import lombok.Data;
import project.asc.AnsimCar.domain.type.Status;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class RentUpdateRequest {
    @NotBlank(message = "상태를 입력하세요.")
    private Status status;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private RentUpdateRequest() {
    }

    public RentUpdateRequest(final Status status, final LocalDateTime rentalDate, final LocalDateTime returnDate) {
        this.status = status;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
}
