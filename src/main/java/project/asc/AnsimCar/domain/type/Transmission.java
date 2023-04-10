package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum Transmission {

    AUTOMATIC("자동"),
    MANUAL("수동");

    @Getter
    private final String description;

    Transmission(String description) {
        this.description = description;
    }
}
