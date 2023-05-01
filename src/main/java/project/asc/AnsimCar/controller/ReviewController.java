package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.asc.AnsimCar.authentication.AccountContext;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.type.Rate;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
import project.asc.AnsimCar.dto.review.request.ReviewCreateRequest;
import project.asc.AnsimCar.dto.review.response.ReviewResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.service.RentService;
import project.asc.AnsimCar.service.ReviewService;
import project.asc.AnsimCar.service.UserCarService;

import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final RentService rentService;
    private final UserCarService userCarService;

    /**
     * enum
     */
    @ModelAttribute("rates")
    public Rate[] rates() {
        return Rate.values();
    }

    /**
     * 카셰어링 목록
     */
    @GetMapping("/rentlist")
    public String rentList(Authentication authentication, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        Page<ReviewResponse> reviewResponses = reviewService.findByAccountId(account.getId(), pageable);

        model.addAttribute("reviews", reviewResponses);

        int nowPage = reviewResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", reviewResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, reviewResponses.getTotalPages()));

        return "review/rentList";
    }

    /**
     * 리뷰 등록
     */
    @GetMapping("/addreview")
    public String addReview(@RequestParam("id") Long id, Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        RentResponse rentResponse = rentService.findInfoById(id);

        if (!rentResponse.isRentalOwner(account.getId()))
            return "redirect:/review/addreviewlist";

        model.addAttribute("rent", rentResponse);
        model.addAttribute("review", new ReviewCreateRequest());

        return "review/addReview";
    }

    @PostMapping("/addreview")
    public String addReview(@Validated @ModelAttribute("review") ReviewCreateRequest reviewCreateRequest, BindingResult bindingResult, @RequestParam("id") Long id, Authentication authentication) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        RentResponse rentResponse = rentService.findInfoById(id);

        if (!rentResponse.isRentalOwner(account.getId()))
            return "redirect:/review/addreviewlist";

        if (bindingResult.hasErrors()) {
            return "review/addReview";
        }

        reviewService.addReview(rentResponse.getUserCarResponse().getId(), rentResponse.getId(), account.getId(), reviewCreateRequest);

        return "redirect:/review/rentlist";
    }

    /**
     * 등록해야 할 리뷰 목록
     */
    @GetMapping("/addreviewlist")
    public String addReviewList(Authentication authentication, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        Page<RentResponse> rentResponses = rentService.findNotReviewedByRentUserId(account.getId(), pageable);

        model.addAttribute("rents", rentResponses);

        int nowPage = rentResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", rentResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, rentResponses.getTotalPages()));

        return "review/addReviewList";
    }

    /**
     * 차량 목록
     */
    @GetMapping("/carlist")
    public String carList(Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        List<UserCarResponse> userCarResponses = userCarService.findByAccountId(account.getId());

        model.addAttribute("userCarResponses", userCarResponses);

        return "review/carList";
    }

    /**
     * 차량 리뷰 목록
     */
    @GetMapping("/carreviewlist")
    public String carReviewList(@RequestParam("id") Long id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<ReviewResponse> reviewResponses = reviewService.findByUserCarId(id, pageable);

        model.addAttribute("reviews", reviewResponses);

        int nowPage = reviewResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", reviewResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, reviewResponses.getTotalPages()));

        return "review/carReviewList";
    }
}
