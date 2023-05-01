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
import project.asc.AnsimCar.aws.s3.S3Upload;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.ImageRequest;
import project.asc.AnsimCar.dto.rent.request.RentCreateRequest;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;
import project.asc.AnsimCar.dto.rent.response.RentItemDetailResponse;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.service.AccountService;
import project.asc.AnsimCar.service.RentService;
import project.asc.AnsimCar.service.UserCarService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rent")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;
    private final UserCarService userCarService;

    private final AccountService accountService;

    private final S3Upload s3Upload;

    @ModelAttribute("carCategories")
    public CarCategory[] carCategories() {
        return CarCategory.values();
    }

    @ModelAttribute("fuels")
    public Fuel[] fuels() {
        return Fuel.values();
    }

    @GetMapping("/list")
    public String list(@ModelAttribute("rentSearchRequest") RentSearchRequest request, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        Page<RentItemDetailResponse> rentItemDetailResponses = rentService.findByAvailable(pageable);

        return getPagingList(pageable, model, rentItemDetailResponses);
    }

    @PostMapping("/list")
    public String searchList(@ModelAttribute("rentSearchRequest") RentSearchRequest request,
                             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {

        Page<RentItemDetailResponse> rentItemDetailResponses = rentService.findAllComplex(request, pageable);

        return getPagingList(pageable, model, rentItemDetailResponses);
    }

    @GetMapping("/list/")
    public String rentInfo(@RequestParam("id") Long id, Model model) {
        RentResponse info = rentService.findInfoById(id);

        model.addAttribute("info", info);
        model.addAttribute("reviewScore", info.getUserCarResponse().rateAverage());

        return "rent/info";
    }

    private String getPagingList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model, Page<RentItemDetailResponse> rentItemDetailResponses) {
        int nowPage = rentItemDetailResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", rentItemDetailResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, rentItemDetailResponses.getTotalPages()));

        return "rent/list";
    }


    @GetMapping("/add")
    public String addRent(Authentication authentication, Model model) {
        modelAddAttributeUserCars(authentication, model);

        model.addAttribute("rent", new RentCreateRequest());

        return "rent/addRent";
    }

    @PostMapping("/add")
    public String addRent(@Validated @ModelAttribute("rent") RentCreateRequest rentCreateRequest, BindingResult bindingResult, Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            modelAddAttributeUserCars(authentication, model);
            return "rent/addRent";
        }

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        rentService.addRent(account.getId(), rentCreateRequest);

        return "redirect:/rent/list";
    }

    private void modelAddAttributeUserCars(Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        List<UserCarResponse> userCarResponses = userCarService.findByAccountId(account.getId());

        List<UserCarResponse> usableCarResponse = new ArrayList<>();

        for (UserCarResponse userCarResponse : userCarResponses) {
            if (userCarResponse.getUsable()) {
                usableCarResponse.add(userCarResponse);
            }
        }

        model.addAttribute("userCars", usableCarResponse);
    }

    @GetMapping("/detail")
    public String rentDetail(@RequestParam("id") Long accountId, Model model) {
        model.addAttribute("account", accountService.findById(accountId));

        return "rent/detail";
    }

    /**
     * 대여 기록
     */
    @GetMapping("/renthistory")
    public String rentHistory(Authentication authentication, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        Page<RentResponse> rentResponses = rentService.findByRentAccountId(account.getId(), pageable);

        model.addAttribute("rentList", rentResponses);

        int nowPage = rentResponses.getPageable().getPageNumber() + 1;
        model.addAttribute("list", rentResponses);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", Math.max(nowPage - 4, 1));
        model.addAttribute("endPage", Math.min(nowPage + 5, rentResponses.getTotalPages()));

        return "rent/rentHistory";
    }

    /**
     * 대여
     */
    @GetMapping("/rental")
    public String rental(@RequestParam("id") Long id, Authentication authentication) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        rentService.updateRentalReturnDate(account.getId(), id, new RentUpdateRequest(Status.WAITING_RENT, LocalDateTime.now(), null));

        return "redirect:/rent/renthistory";
    }

    /**
     * 사진 등록 화면 이동
     */
    @GetMapping("/renthistory/photo")
    public String photo(@RequestParam("id") Long rentId) {
        return "rent/addPhoto";
    }


    /**
     * 사진 등록
     */
    @PostMapping("/renthistory/photo")
    public String photo(@RequestParam("id") Long rentId, @ModelAttribute ImageRequest imageRequest, Authentication authentication) throws IOException {

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();
        Long accountId = account.getId();

        //TODO 반환받은 url을 플라스크 서버로 전송 -> 플라스크 서버에서 해당 이미지를 분석하여 s3에 저장 후 url 반환 -> 이 url을 DB에 저장
        //TODO RestTemplate을 사용해서 플라스크 서버 API를 호출 해야 할듯하다.
        String url = s3Upload.upload(accountId, rentId, imageRequest.getCarFront());
        s3Upload.upload(accountId, rentId, imageRequest.getCarRear());
        s3Upload.upload(accountId, rentId, imageRequest.getCarLeft());
        s3Upload.upload(accountId, rentId, imageRequest.getCarRight());

        return "rent/addPhoto";
    }
}
