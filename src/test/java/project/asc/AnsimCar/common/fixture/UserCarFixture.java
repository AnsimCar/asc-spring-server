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
    public static final String 모델명 = "아반떼_CN7";
    public static final CarCategory 차종 = CarCategory.SMALL;
    public static final String 제조사 = "현대";
    public static final Fuel 연료종류 = Fuel.GASOLINE;
    public static final String 차량번호 = "111우_1234";

    public static UserCar createUserCar(Account 유저) {
        UserCarFixture.유저 = 유저;
        return UserCar.builder()
                .account(유저)
                .carModel(모델명)
                .carCategory(차종)
                .manufacturer(제조사)
                .fuel(연료종류)
                .carNumber(차량번호)
                .build();
    }

    public static UserCarCreateRequest createUserCarRequest() {
        return new UserCarCreateRequest(
                모델명,
                차종,
                제조사,
                연료종류,
                차량번호
        );
    }

    public static UserCarUpdateRequest updateUserCarRequest() {
        return new UserCarUpdateRequest(
                "소나타",
                CarCategory.MEDIUM,
                "현대",
                Fuel.GASOLINE
        );
    }


    public static MultiValueMap<String, Object> createUserCarParam() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("account", 유저);
        params.add("carModel", 모델명);
        params.add("carCategory", 차종);
        params.add("manufacturer", 제조사);
        params.add("fuel", 연료종류);
        params.add("carNumber", 차량번호);
        return params;
    }
}
