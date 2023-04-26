package project.asc.AnsimCar.common.fixture;

import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.RentCreateRequest;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RentFixture {

    public static final LocalDateTime 등록날짜 = LocalDateTime.of(2023, 4, 12, 12, 0);

    public static final Status 상태 = Status.AVAILABLE;

    public static final LocalDateTime 대여날짜 = LocalDateTime.of(2023, 4, 21, 8, 0);

    public static final LocalDateTime 반납날짜 = LocalDateTime.of(2023, 4, 22, 8, 0);

    public static final List<BeforeImage> beforeImages = new ArrayList<>();

    public static final List<AfterImage> afterImages = new ArrayList<>();


    public static Rent createRent(UserCar userCar, Account account, Address address) {
        return Rent.builder()
                .userCar(userCar)
                .account(account)
                .address(address)
                .registrationDate(등록날짜)
                .status(상태)
                .rentalDate(대여날짜)
                .returnDate(반납날짜)
                .build();
    }

    public static RentCreateRequest createRentCreateRequest(Long userCarId, Address address) {
        return new RentCreateRequest(userCarId, address.getSido(), address.getSigungu(), address.getEupmyeondong());
    }

    public static RentUpdateRequest createRentUpdateRequest1() {
        return new RentUpdateRequest(
                Status.WAITING_RENT,
                null,
                null
        );
    }

    public static RentUpdateRequest createRentUpdateRequest2() {
        return new RentUpdateRequest(
                Status.RENTING,
                LocalDateTime.now(),
                LocalDateTime.MAX
        );
    }

}
