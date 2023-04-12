package project.asc.AnsimCar.common.fixture;

import project.asc.AnsimCar.domain.Address;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.dto.address.request.AddressCreateRequest;

import javax.persistence.*;

public class AddressFixture {

    public static final String 시_도 = "대전";

    public static final String 시_군_구 = "유성구";

    public static final String 읍_면_동 = "지족동";

    public static Address createAddress() {
        return Address.builder()
                .sido(시_도)
                .sigungu(시_군_구)
                .eupmyeondong(읍_면_동)
                .build();
    }

    public static AddressCreateRequest createAddressCreateRequest() {
        return new AddressCreateRequest(
                시_도,
                시_군_구,
                읍_면_동
        );
    }

}
