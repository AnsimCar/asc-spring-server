package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;
import project.asc.AnsimCar.domain.common.BaseEntity;
import project.asc.AnsimCar.domain.type.Role;
import project.asc.AnsimCar.dto.account.request.AccountUpdateRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(indexes = {
        @Index(columnList = "email")
})
@Entity
@Getter
public class Account extends BaseEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<UserCar> userCars = new ArrayList<>();

    protected Account() {}

    @Builder
    public Account(String username, String email, String password, String phoneNumber, Integer age, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.role = role;
    }


    public void updateAccount(AccountUpdateRequest accountUpdateRequest) {
        this.username = accountUpdateRequest.getUsername();
        this.email = accountUpdateRequest.getEmail();
        this.phoneNumber = accountUpdateRequest.getPhoneNumber();
        this.age = accountUpdateRequest.getAge();
        this.role = Role.ROLE_USER;
    }


    /**
     * DB에 저장하기전 password 암호화를 위한 setter
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
