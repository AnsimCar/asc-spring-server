package project.asc.AnsimCar.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.common.fixture.ReviewFixture;
import project.asc.AnsimCar.domain.*;
import project.asc.AnsimCar.dto.review.request.ReviewCreateRequest;
import project.asc.AnsimCar.dto.review.request.ReviewUpdateRequest;
import project.asc.AnsimCar.dto.review.response.ReviewResponse;
import project.asc.AnsimCar.exception.review.ReviewNotFoundException;
import project.asc.AnsimCar.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AddressFixture.createAddress;
import static project.asc.AnsimCar.common.fixture.RentFixture.createRent;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar1;

class ReviewServiceTest extends ServiceTest {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserCarRepository userCarRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RentRepository rentRepository;
    @Autowired
    AccountRepository accountRepository;

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
        Review review1 = ReviewFixture.createReview(rentCar, rent1, rentAccount1, 4, "좋아요.", LocalDateTime.now());
        Review review2 = ReviewFixture.createReview(rentCar, rent2, rentAccount2, 5, null, LocalDateTime.now());
        Review review3 = ReviewFixture.createReview(rentCar, rent3, rentAccount3, 3, "그저 그래요.", LocalDateTime.now());

        reviews = Arrays.asList(review1, review2, review3);

        reviewRepository.saveAll(reviews);
    }

    @Test
    @DisplayName("리뷰 등록 & 아이디로 리뷰 조회")
    void addReview() {
        //given
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(rentCar.getId(), rent1.getId(), rentAccount1.getId(), 4, "좋아요.", LocalDateTime.now());

        //when
        ReviewResponse reviewResponse = reviewService.addReview(reviewCreateRequest.getUserCarId(), reviewCreateRequest.getRentId(), reviewCreateRequest.getAccountId(), reviewCreateRequest);
        ReviewResponse result = reviewService.findById(reviewResponse.getId());

        //then
        assertThat(result.getId()).isEqualTo(reviewResponse.getId());
    }

    @Test
    @DisplayName("렌트 차량 id로 리뷰 조회하기")
    void findByUserCarId() {
        //given
        given();

        //when
        List<ReviewResponse> results = reviewService.findByUserCarId(rentCar.getId());

        //then
        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("렌트 id로 리뷰 조회하기")
    void findByRentId() {
        //given
        given();

        //when
        ReviewResponse result = reviewService.findByRentId(rent1.getId());

        //then
        assertThat(result.getAccountResponse().getEmail()).isEqualTo(rentAccount1.getEmail());
    }

    @Test
    @DisplayName("계정 ID로 리뷰 조회하기")
    void findByAccountId() {
        //given
        given();

        //when
        List<ReviewResponse> results = reviewService.findByAccountId(rentAccount1.getId());

        //then
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 수정하기")
    void updateReview() {
        //given
        given();
        ReviewResponse review = reviewService.findByUserCarId(rentCar.getId()).get(0);
        ReviewUpdateRequest reviewUpdateRequest = new ReviewUpdateRequest(2, "생각해 보니 별로네요.");

        //when
        reviewService.updateReview(review.getAccountResponse().getId(), review.getId(), reviewUpdateRequest);
        ReviewResponse result = reviewService.findByUserCarId(rentCar.getId()).get(0);

        //then
        assertThat(result.getRate()).isEqualTo(2);
        assertThat(result.getDescription()).isEqualTo(reviewUpdateRequest.getDescription());
    }

    @Test
    @DisplayName("리뷰 삭제하기")
    void deleteReview() {
        //given
        given();
        ReviewResponse review = reviewService.findByUserCarId(rentCar.getId()).get(0);

        //when
        reviewService.deleteReview(review.getAccountResponse().getId(), review.getId());

        //then
        Assertions.assertThrows(ReviewNotFoundException.class, () -> {
            reviewService.findById(rent1.getId());
        });
    }
}