package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum Status {
    AVAILABLE("유휴"),
    WAITING_RENT("렌트 대기 중"),
    RENTING("렌트 중"),
    WAITING_RETURN("반납 대기 중"),
    RETURN("반납 완료");

    @Getter
    private final String description;

    Status(String description) {
        this.description = description;
    }
}
