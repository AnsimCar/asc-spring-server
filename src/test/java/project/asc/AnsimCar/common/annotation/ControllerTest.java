package project.asc.AnsimCar.common.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class ControllerTest {

    protected MockMvc mvc;

    @BeforeEach
    public void setMvc(@Autowired MockMvc mvc) {     //원래 생성자가 하나면 @Autowired 가 자동을 붙어지지만 test 패키지에서는 아니다.
        this.mvc = mvc;
    }
}
