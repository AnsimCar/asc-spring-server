package project.asc.AnsimCar.domain.type;

import lombok.Getter;

public enum Fuel {
    GASOLINE("휘발유"),
    DIESEL("경유"),
    ELECTRONIC("전기");

    @Getter
    private final String description;

    Fuel(String description) {
        this.description = description;
    }
}
