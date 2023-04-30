package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum Status {
    AVAILABLE("유휴"),
    WAITING_RENT("카셰어링 대기 중"),
    RENTING("카셰어링 중"),
    WAITING_RETURN("반납 대기 중"),
    RETURN("반납 완료"),
    STOP("중지");

    @Getter
    private final String description;

    Status(String description) {
        this.description = description;
    }
}
