package project.asc.AnsimCar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.dto.account.request.AccountPasswordResetRequest;
import project.asc.AnsimCar.exception.account.EmailExistException;
import project.asc.AnsimCar.exception.account.PasswordCheckException;
import project.asc.AnsimCar.exception.usercar.UserCarException;


@ControllerAdvice
public class ExceptionController {

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
