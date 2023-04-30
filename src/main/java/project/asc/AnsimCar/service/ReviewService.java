package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.Review;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.dto.rent.response.RentItemDetailResponse;
import project.asc.AnsimCar.dto.review.request.ReviewCreateRequest;
import project.asc.AnsimCar.dto.review.request.ReviewUpdateRequest;
import project.asc.AnsimCar.dto.review.response.ReviewResponse;
import project.asc.AnsimCar.exception.account.AccountNotFoundException;
import project.asc.AnsimCar.exception.rent.RentNotFoundException;
import project.asc.AnsimCar.exception.rent.RentOwnerException;
import project.asc.AnsimCar.exception.review.ReviewNotFoundException;
import project.asc.AnsimCar.exception.review.ReviewOwnerException;
import project.asc.AnsimCar.exception.usercar.UserCarNotFoundException;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.repository.ReviewRepository;
import project.asc.AnsimCar.repository.UserCarRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserCarRepository userCarRepository;
    private final RentRepository rentRepository;
    private final AccountRepository accountRepository;

    /**
     * 리뷰 등록
     */
    public ReviewResponse addReview(final Long userCarId, final Long rentId, final Long accountId, final ReviewCreateRequest reviewCreateRequest) {
        UserCar userCar = findUserCarById(userCarId);
        Rent rent = findRentById(rentId);
        validateOwner(accountId, rent);
        Account account = findAccountById(accountId);

        Review review = createReview(userCar, rent, account, reviewCreateRequest);

        reviewRepository.save(review);

        return ReviewResponse.from(review);
    }

    private void validateOwner(Long accountId, Rent rent) {
        if (!rent.isOwner(accountId)) {
            throw new RentOwnerException();
        }
    }

    private Review createReview(UserCar userCar, Rent rent, Account account, ReviewCreateRequest reviewCreateRequest) {
        return Review.builder()
                .userCar(userCar)
                .rent(rent)
                .account(account)
                .rate(reviewCreateRequest.getRate())
                .description(reviewCreateRequest.getDescription())
                .reviewDate(reviewCreateRequest.getReviewDate())
                .build();
    }

    private UserCar findUserCarById(Long userCarId) {
        return userCarRepository.findById(userCarId).orElseThrow(UserCarNotFoundException::new);
    }

    private Rent findRentById(Long rentId) {
        return rentRepository.findById(rentId).orElseThrow(RentNotFoundException::new);
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }

    /**
     * 카셰어링 Id로 검색
     */
    public ReviewResponse findById(final Long id) {
        return ReviewResponse.from(reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new));
    }

    /**
     * 카셰어링 차량으로 리뷰 검색
     */
    public List<ReviewResponse> findByUserCarId(final Long userCarId) {
        List<Review> reviews = reviewRepository.findByUserCar_Id(userCarId);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponses.add(ReviewResponse.from(review));
        }

        return reviewResponses;
    }

    public Page<ReviewResponse> findByUserCarId(final Long userCarId, Pageable pageable) {

        return reviewRepository.findByUserCar_Id(userCarId, pageable).map(ReviewResponse::from);
    }

    /**
     * 카셰어링 Id로 리뷰 검색
     */
    public ReviewResponse findByRentId(final Long rentId) {
        return ReviewResponse.from(reviewRepository.findByRent_Id(rentId).orElseThrow(ReviewNotFoundException::new));
    }

    /**
     * 계정 Id로 리뷰 검색
     */
    public List<ReviewResponse> findByAccountId(final Long accountId) {
        List<Review> reviews = reviewRepository.findByAccount_Id(accountId);
        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponses.add(ReviewResponse.from(review));
        }

        return reviewResponses;
    }

    public Page<ReviewResponse> findByAccountId(final Long accountId, Pageable pageable) {
        return reviewRepository.findByAccount_Id(accountId, pageable).map(ReviewResponse::from);
    }

    /**
     * 리뷰 수정
     */
    public void updateReview(final Long accountId, final Long reviewId, final ReviewUpdateRequest reviewUpdateRequest) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        validateOwner(accountId, review);

        review.updateReview(reviewUpdateRequest);
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReview(final Long accountId, final Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        validateOwner(accountId, review);

        reviewRepository.delete(review);
    }

    private void validateOwner(Long accountId, Review review) {
        if (!review.isOwner(accountId)) {
            throw new ReviewOwnerException();
        }
    }
}
