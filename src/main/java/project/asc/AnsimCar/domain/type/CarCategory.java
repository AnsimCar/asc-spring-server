package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum CarCategory {

    LIGHT("경차"),
    SMALL("소형차"),
    SEMI_MEDIUM("준중형"),
    MEDIUM("중형"),
    SEMI_FORMAL("준대형"),
    LARGE("대형");

    @Getter
    private final String description;

    CarCategory(String description) {
        this.description = description;
    }
}
