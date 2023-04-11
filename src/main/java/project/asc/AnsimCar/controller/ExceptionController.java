package project.asc.AnsimCar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import project.asc.AnsimCar.dto.account.request.AccountCreateRequest;
import project.asc.AnsimCar.exception.Account.EmailExistException;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EmailExistException.class)
    public String EmailExistExHandler(EmailExistException e, Model model) {
        model.addAttribute("account", AccountCreateRequest.builder().build());
        model.addAttribute("exception", e.getMessage());

        return "account/register";
    }
}
