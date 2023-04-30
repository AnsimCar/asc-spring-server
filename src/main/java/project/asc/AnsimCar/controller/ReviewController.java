package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.asc.AnsimCar.authentication.AccountContext;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
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
