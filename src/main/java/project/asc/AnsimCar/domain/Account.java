package project.asc.AnsimCar.domain;

import lombok.Getter;
import project.asc.AnsimCar.domain.type.Role;
import javax.persistence.*;

@Table(indexes = {
        @Index(columnList = "email")
})
@Entity
@Getter
public class Account {


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


    protected Account() {}

    private Account(String username, String email, String password, String phoneNumber, Integer age, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.role = role;
    }

    /**
     * 팩토리 패턴
     */
    public static Account of(String username, String email, String password, String phoneNumber, Integer age) {
        return new Account(username, email, password, phoneNumber, age, Role.ROLE_USER);
    }

    /**
     * DB에 저장하기전 password 암호화를 위한 setter
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
