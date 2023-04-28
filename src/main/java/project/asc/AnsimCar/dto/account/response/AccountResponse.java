package project.asc.AnsimCar.dto.account.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Account;

@Data
public class AccountResponse {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer age;

    private String bankName;

    private String bankAccount;

    @Builder
    public AccountResponse(Long id, String username, String email, String password, String phoneNumber, Integer age, String bankName, String bankAccount) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
    }

    /**
     * 엔티티 -> ResponseDto
     */
    public static AccountResponse from(Account entity) {
        if (entity == null)
            return null;
        return new AccountResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getAge(),
                entity.getBankName(),
                entity.getBankAccount()
        );
    }

    /**
     * ResponseDto -> 엔티티
     */
    public Account toEntity() {
        return Account.builder()
                .username(username)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .age(age)
                .bankName(bankName)
                .bankAccount(bankAccount)
                .build();
    }
}
