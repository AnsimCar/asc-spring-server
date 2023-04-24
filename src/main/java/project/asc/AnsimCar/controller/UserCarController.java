package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.asc.AnsimCar.authentication.AccountContext;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.dto.usercar.request.UserCarCreateRequest;
import project.asc.AnsimCar.dto.usercar.request.UserCarUpdateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.service.UserCarService;

import javax.validation.Valid;

@Controller
@RequestMapping("/usercar")
@RequiredArgsConstructor
public class UserCarController {

    private final UserCarService userCarService;

    /**
     * enum
     */
    @ModelAttribute("carCategories")
    public CarCategory[] carCategories() {
        return CarCategory.values();
    }

    @ModelAttribute("fuels")
    public Fuel[] fuels() {
        return Fuel.values();
    }

    /**
     * GET 차량 등록
     */
    @GetMapping("/add")
    public String addCar(Model model) {

        model.addAttribute("userCar", UserCarCreateRequest.builder().build());

        return "usercar/addUserCar";
    }

    /**
     * POST 차량 등록
     */
    @PostMapping("/add")
    public String addCar(@Validated @ModelAttribute("userCar") UserCarCreateRequest userCarCreateRequest,
                         BindingResult bindingResult,
                         Authentication authentication
    ) {

        if (bindingResult.hasErrors()) return "usercar/addUserCar";

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        userCarService.addUserCar(account.getId(), userCarCreateRequest);

        return "redirect:/usercar/list";
    }

    /**
     * 차량 목록
     */
    @GetMapping("/list")
    public String carList(Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        model.addAttribute("userCarList", userCarService.findByAccountId(account.getId()));

        return "usercar/list";
    }

    /**
     * 차량 상세 정보
     */
    @GetMapping("/list/")
    public String carDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("userCar", userCarService.findById(id));

        return "usercar/info";
    }

    /**
     * 차량 수정
     */
    @GetMapping("/update")
    public String updateCar(@RequestParam("id") Long id, Model model) {

        model.addAttribute("updateCar", userCarService.findById(id));

        return "usercar/update";
    }

    @PostMapping("/update")
    public String updateCar(@RequestParam("id") Long id,
                            @Validated @ModelAttribute("updateCar") UserCarUpdateRequest userCarUpdateRequest,
                            Authentication authentication,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "usercar/info";

        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        userCarService.updateUserCar(account.getId(), id, userCarUpdateRequest);

        return "redirect:/usercar/list/?id=" + id;
    }

    /**
     * 차량 삭제
     */
    @GetMapping("/delete")
    public String deleteCar(@RequestParam("id") Long id, Authentication authentication) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();

        userCarService.deleteUserCar(account.getId(), id);

        return "redirect:/usercar/list";
    }
}
