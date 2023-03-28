package project.asc.AnsimCar.dto.account;
import project.asc.AnsimCar.domain.Account;


public record AccountRequest(
        String username,

        String email,

        String password,

        String phoneNumber,

        Integer age
) {

    public static AccountRequest of(String username, String email, String password, String phoneNumber, Integer age) {
        return new AccountRequest(username, email, password, phoneNumber, age);
    }

    /**
     * 엔티티 -> Dto
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
     * Dto -> 엔티티
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
