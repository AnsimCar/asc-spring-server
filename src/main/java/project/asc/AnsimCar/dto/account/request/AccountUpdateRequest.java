package project.asc.AnsimCar.dto.account.request;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.type.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AccountUpdateRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "나이를 입력해주세요.")
    private Integer age;

    @NotBlank(message = "은행을 입력해주세요.")
    private String bankName;

    @NotBlank(message = "계좌번호를 입력해주세요.")
    private String bankAccount;

    @Builder
    public AccountUpdateRequest(final String username, final String email, final String phoneNumber, final Integer age, final String bankName, final String bankAccount) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
    }

    /**
     * RequestDto -> 엔티티
     */
    public Account toEntity() {
        return Account.builder()
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .age(age)
                .bankName(bankName)
                .bankAccount(bankAccount)
                .role(Role.ROLE_USER)
                .build();
    }

}
