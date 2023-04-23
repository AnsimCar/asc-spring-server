package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.asc.AnsimCar.authentication.AccountContext;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountUpdateRequest;
import project.asc.AnsimCar.dto.account.response.AccountResponse;
import project.asc.AnsimCar.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * 뷰 이동(login)
     */
    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    /**
     * logout 컨트롤러
     */
    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();     //인증객체 조회

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);       //로그아웃 처리
        }

        return "redirect:/login";
    }

    /**
     * 뷰 이동(register)
     * ---
     * register 메서드의 검증 통과 데이터는 유지하는 로직(@ModelAttribute("account"))을 추가하면서 뷰에서 모델에 접근하는 코드가 강제되었다.
     * 그래서 빈 모델이라도 넘겨주어야 뷰 이동이 가능해졌다.
     */
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("account", AccountResponse.builder().build());

        return "account/register";
    }

    /**
     * register 컨트롤러
     * ---
     * 입력 데이터 검증 로직을 추가하면서 만약 입력데이터 중 몇개의 데이터만 검증을 통과하였다면 통과한 데이터는 유지해야 한다.
     * 그래서 기존에는 사용하지 않던 @ModelAttribute("account")를 추가하고 뷰에서 검증 통과 데이터를 유지할 수 있게 추가 개발하였다.
     */
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute("account") AccountCreateRequest accountCreateRequest, BindingResult bindingResult) {

        //입력 데이터 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) return "account/register";

        accountService.register(accountCreateRequest);

        return "redirect:/";
    }


    @GetMapping("/mypage/account/info")
    public String myPageInfo(Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        model.addAttribute(accountContext.getAccount());

        return "account/info";
    }

    @GetMapping("/mypage/account/update")
    public String myPageUpdateForm(Authentication authentication, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        model.addAttribute(accountContext.getAccount());

        return "account/update";
    }

    @PostMapping("/mypage/account/update")
    public String myPageUpdate(Authentication authentication, @Validated @ModelAttribute("account") AccountUpdateRequest accountUpdateRequest, Model model) {
        AccountContext accountContext = (AccountContext) authentication.getPrincipal();
        Account account = accountContext.getAccount();
        model.addAttribute(account);

        accountService.update(account, accountUpdateRequest);

        return "redirect:/logout";
    }

}
