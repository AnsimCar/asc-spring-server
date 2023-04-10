package project.asc.AnsimCar.dto.account;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Account;

@Data
public class AccountDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer age;

    @Builder
    public AccountDto(Long id, String username, String email, String password, String phoneNumber, Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static AccountDto from(Account entity) {
        return new AccountDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getAge()
        );
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
