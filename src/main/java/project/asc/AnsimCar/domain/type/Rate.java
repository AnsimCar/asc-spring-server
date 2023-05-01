package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum Rate {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    @Getter
    private final int description;

    Rate(int description) {
        this.description = description;
    }
}