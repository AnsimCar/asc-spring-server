package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.AccountRequest;
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

        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);       //로그아웃 처리
        }

        return "redirect:/login";
    }

    /**
     * 뷰 이동(register)
     */
    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    /**
     * register 컨트롤러
     */
    @PostMapping("/register")
    public String register(AccountRequest accountRequest) {

        accountService.register(accountRequest);

        return "redirect:/";
    }

}
