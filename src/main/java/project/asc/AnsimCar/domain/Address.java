package project.asc.AnsimCar.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String sido;

    private String sigungu;

    private String eupmyeondong;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private Rent rent;

    protected Address() {
    }

    @Builder
    public Address(String sido, String sigungu, String eupmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeondong = eupmyeondong;
    }
}
