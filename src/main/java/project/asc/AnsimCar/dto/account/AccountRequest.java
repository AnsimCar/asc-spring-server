package project.asc.AnsimCar.dto.account;
import project.asc.AnsimCar.domain.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public record AccountRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        String username,

        @NotBlank(message = "이메일을 입력해주세요.") @Email(message = "이메일 형식을 맞춰주세요.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "핸드폰 번호를 입력해주세요.")
        String phoneNumber,

        @NotNull(message = "나이를 입력해주세요.")
        Integer age
) {

    public static AccountRequest of(String username, String email, String password, String phoneNumber, Integer age) {
        return new AccountRequest(username, email, password, phoneNumber, age);
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static AccountRequest from(Account entity) {
        return new AccountRequest(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getAge());
    }

    /**
     * RequestDto -> 엔티티
     */
    public Account toEntity() {
        return Account.of(
                username,
                email,
                password,
                phoneNumber,
                age
        );
    }

}
