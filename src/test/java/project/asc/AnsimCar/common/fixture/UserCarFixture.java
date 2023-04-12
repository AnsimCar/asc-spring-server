package project.asc.AnsimCar.common.fixture;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;

public class UserCarFixture {
    public static Account 유저;
    public static final String 모델명1 = "아반떼_CN7";
    public static final CarCategory 차종1 = CarCategory.SMALL;
    public static final String 제조사1 = "현대";
    public static final Fuel 연료종류1 = Fuel.GASOLINE;
    public static final String 차량번호1 = "111우_1234";

    public static UserCar createUserCar1(Account 유저) {
        UserCarFixture.유저 = 유저;
        return UserCar.builder()
                .account(유저)
                .carModel(모델명1)
                .carCategory(차종1)
                .manufacturer(제조사1)
                .fuel(연료종류1)
                .carNumber(차량번호1)
                .build();
    }

    public static UserCarCreateRequest createUserCarRequest1() {
        return new UserCarCreateRequest(
                모델명1,
                차종1,
                제조사1,
                연료종류1,
                차량번호1
        );
    }

    public static UserCarUpdateRequest updateUserCarRequest1() {
        return new UserCarUpdateRequest(
                "소나타",
                CarCategory.MEDIUM,
                "현대",
                Fuel.GASOLINE
        );
    }


    public static final String 모델명2 = "아반떼_CN7";
    public static final CarCategory 차종2 = CarCategory.SMALL;
    public static final String 제조사2 = "현대";
    public static final Fuel 연료종류2 = Fuel.GASOLINE;
    public static final String 차량번호2 = "111우_1234";

    public static UserCar createUserCar2(Account 유저) {
        UserCarFixture.유저 = 유저;
        return UserCar.builder()
                .account(유저)
                .carModel(모델명2)
                .carCategory(차종2)
                .manufacturer(제조사2)
                .fuel(연료종류2)
                .carNumber(차량번호2)
                .build();
    }


    public static UserCarCreateRequest createUserCarRequest2() {
        return new UserCarCreateRequest(
                모델명2,
                차종2,
                제조사2,
                연료종류2,
                차량번호2
        );
    }

    public static UserCarUpdateRequest updateUserCarRequest2() {
        return new UserCarUpdateRequest(
                "소나타",
                CarCategory.MEDIUM,
                "현대",
                Fuel.DIESEL
        );
    }


    public static MultiValueMap<String, Object> createUserCarParam() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("account", 유저);
        params.add("carModel", 모델명1);
        params.add("carCategory", 차종1);
        params.add("manufacturer", 제조사1);
        params.add("fuel", 연료종류1);
        params.add("carNumber", 차량번호1);
        return params;
    }
}
