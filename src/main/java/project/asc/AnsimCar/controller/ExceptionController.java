package project.asc.AnsimCar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import project.asc.AnsimCar.authentication.AccountContext;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountPasswordResetRequest;
import project.asc.AnsimCar.dto.rent.request.RentCreateRequest;
import project.asc.AnsimCar.dto.usercar.response.UserCarResponse;
import project.asc.AnsimCar.exception.account.EmailExistException;
import project.asc.AnsimCar.exception.account.PasswordCheckException;
import project.asc.AnsimCar.service.UserCarService;

import java.util.List;


@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final UserCarService userCarService;

    /**
     * 이메일 중복 예외
     */
    @ExceptionHandler(EmailExistException.class)
    public String EmailExistExHandler(EmailExistException e, Model model) {
        model.addAttribute("account", AccountCreateRequest.builder().build());
        model.addAttribute("exception", e.getMessage());

        return "account/register";
    }

    /**
     * 비밀번호 검증 예외
     */
    @ExceptionHandler(PasswordCheckException.class)
    public String PasswordCheckExHandler(PasswordCheckException e, Model model) {
        model.addAttribute("password", AccountPasswordResetRequest.builder().build());
        model.addAttribute("exception", e.getMessage());

        return "account/passwordReset";
    }


}
