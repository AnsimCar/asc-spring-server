package project.asc.AnsimCar.common.fixture;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountPasswordResetRequest;
import project.asc.AnsimCar.dto.account.request.AccountUpdateRequest;

public class AccountFixture {

    public static final String 이름 = "Joe";
    public static final String 이메일 = "account@gmail.com";
    public static final String 비밀번호 = "1234";
    public static final String 핸드폰번호 = "010";
    public static final Integer 나이 = 25;

    public static Account createAccount() {
        return Account.builder()
                .username(이름)
                .email(이메일)
                .password(비밀번호)
                .phoneNumber(핸드폰번호)
                .age(나이)
                .build();
    }

    public static AccountCreateRequest createAccountRequest() {
        return AccountCreateRequest.builder()
                .username(이름)
                .email(이메일)
                .password(비밀번호)
                .phoneNumber(핸드폰번호)
                .age(나이)
                .build();
    }

    public static AccountUpdateRequest createAccountUpdateRequest() {
        return AccountUpdateRequest.builder()
                .username("Woo")
                .email("new@gmail.com")
                .phoneNumber("070")
                .age(26)
                .build();
    }

    //비밀번호 재설정 성공 케이스
    public static AccountPasswordResetRequest createAccountPasswordResetRequestSuccess() {
        return AccountPasswordResetRequest.builder()
                .currentPassword(비밀번호)
                .newPassword("5678")
                .checkPassword("5678")
                .build();
    }

    //기존 비밀번호와 일치하지 않는 실패 케이스
    public static AccountPasswordResetRequest createAccountPasswordResetRequestFail1() {
        return AccountPasswordResetRequest.builder()
                .currentPassword("기존 비밀번호 불일치")
                .newPassword("5678")
                .checkPassword("5678")
                .build();
    }

    //새로운 비밀번호를 입력한 두 개의 값이 일치하지 않는 실패 케이스
    public static AccountPasswordResetRequest createAccountPasswordResetRequestFail2() {
        return AccountPasswordResetRequest.builder()
                .currentPassword(비밀번호)
                .newPassword("5679")
                .checkPassword("5678")
                .build();
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

    public static MultiValueMap<String, String> updateAccountParam() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "Woo");
        params.add("email", "new@gmail.com");
        params.add("phoneNumber", "070");
        params.add("age", "26");
        return params;
    }
}
