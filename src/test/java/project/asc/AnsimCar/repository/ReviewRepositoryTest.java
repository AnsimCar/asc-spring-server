package project.asc.AnsimCar.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.RepositoryTest;
import project.asc.AnsimCar.common.fixture.ReviewFixture;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.domain.type.Rate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AddressFixture.createAddress;
import static project.asc.AnsimCar.common.fixture.RentFixture.createRent;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar1;

class ReviewRepositoryTest extends RepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserCarRepository userCarRepository;
    @Autowired
    RentRepository rentRepository;
    @Autowired
    AddressRepository addressRepository;

    List<Rent> rents = new ArrayList<>();
    List<Review> reviews = new ArrayList<>();

    UserCar rentCar;

    Account rentAccount1;
    Account rentAccount2;
    Account rentAccount3;

    Rent rent1;
    Rent rent2;
    Rent rent3;

    @BeforeEach
    void beforeEach() {
        Account account = createAccount();
        accountRepository.save(account);
        rentCar = createUserCar1(account);
        userCarRepository.save(rentCar);

        Address address = createAddress();
        addressRepository.save(address);

        rentAccount1 = accountRepository.save(Account.builder().username("Woo").email("jun@naver.com").password("ppp").phoneNumber("0102222").age(25).build());
        rentAccount2 = accountRepository.save(Account.builder().username("Song").email("song@naver.com").password("qqq").phoneNumber("0103333").age(25).build());
        rentAccount3 = accountRepository.save(Account.builder().username("Lee").email("lee@naver.com").password("www").phoneNumber("0104444").age(28).build());

        rent1 = createRent(rentCar, rentAccount1, address);
        rent2 = createRent(rentCar, rentAccount2, address);
        rent3 = createRent(rentCar, rentAccount3, address);

        rents = Arrays.asList(rent1, rent2, rent3);

        rentRepository.saveAll(rents);
    }

    void given() {
        Review review1 = ReviewFixture.createReview(rentCar, rent1, rentAccount1, Rate.THREE, "좋아요.", LocalDateTime.now());
        Review review2 = ReviewFixture.createReview(rentCar, rent2, rentAccount2, Rate.FOUR, null, LocalDateTime.now());
        Review review3 = ReviewFixture.createReview(rentCar, rent3, rentAccount3, Rate.ONE, "그저 그래요.", LocalDateTime.now());

        reviews = Arrays.asList(review1, review2, review3);

        reviewRepository.saveAll(reviews);
    }


    @Test
    @DisplayName("자동차로 리뷰 조회")
    void findByUserCar_Id() {
        //given
        given();
        UserCar userCar = userCarRepository.findById(rentCar.getId()).orElseThrow();

        //when
        List<Review> result = reviewRepository.findByUserCar_Id(userCar.getId());

        //then
        assertThat(result.size()).isEqualTo(reviews.size());
    }

    @Test
    @DisplayName("카셰어링 기록으로 리뷰 조회")
    void findByRent_Id() {
        //given
        given();
        Rent rent = rentRepository.findById(rent1.getId()).orElseThrow();

        //when
        Review result = reviewRepository.findByRent_Id(rent.getId()).get();

        //then
        assertThat(result.getAccount().getEmail()).isEqualTo(rentAccount1.getEmail());
    }

    @Test
    @DisplayName("유저로 리뷰 조회")
    void findByAccount_Id() {
        //given
        given();
        Account account = accountRepository.findById(rentAccount3.getId()).orElseThrow();

        //when
        List<Review> result = reviewRepository.findByAccount_Id(account.getId());

        //then
        assertThat(result.size()).isEqualTo(1);
    }
}