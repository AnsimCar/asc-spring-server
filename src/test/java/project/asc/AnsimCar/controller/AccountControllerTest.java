package project.asc.AnsimCar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.asc.AnsimCar.authentication.CustomUserDetailService;
import project.asc.AnsimCar.common.annotation.ControllerTest;
import project.asc.AnsimCar.common.fixture.AccountFixture;
import project.asc.AnsimCar.config.SecurityConfig;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.service.AccountService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccountRequest;
import static project.asc.AnsimCar.common.fixture.AccountFixture.이름;


@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
class AccountControllerTest extends ControllerTest {

    @MockBean
    private AccountService accountService;

    @DisplayName("회원가입")
    @Test
    public void 회원가입() throws Exception {
        //given
        willDoNothing().given(accountService).register(any());

        //when & then
        mvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(AccountFixture.createAccountParam()))

                .andExpect(status().is3xxRedirection());

        then(accountService).should().register(any());
    }


}