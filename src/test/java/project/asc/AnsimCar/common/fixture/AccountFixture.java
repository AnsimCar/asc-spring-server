package project.asc.AnsimCar.common.fixture;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.AccountRequest;

public class AccountFixture {

    public static final String 이름 = "Joe";
    public static final String 이메일 = "account@gmail.com";
    public static final String 비밀번호 = "1234";
    public static final String 핸드폰번호 = "010";
    public static final Integer 나이 = 25;

    public static Account createAccount() {
        return Account.of(
                이름,
                이메일,
                비밀번호,
                핸드폰번호,
                나이
        );
    }

    public static AccountRequest createAccountRequest() {
        return AccountRequest.of(
                이름,
                이메일,
                비밀번호,
                핸드폰번호,
                나이
        );
    }


    public static MultiValueMap<String, String> createAccountParam() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", 이름);
        params.add("email", 이메일);
        params.add("password", 비밀번호);
        params.add("phoneNumber", 핸드폰번호);
        params.add("age", 나이.toString());
        return params;
    }

}