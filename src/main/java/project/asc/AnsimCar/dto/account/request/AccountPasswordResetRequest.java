package project.asc.AnsimCar.dto.account.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccountPasswordResetRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String checkPassword;

    public AccountPasswordResetRequest() {
    }

    @Builder
    public AccountPasswordResetRequest(String currentPassword, String newPassword, String checkPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.checkPassword = checkPassword;
    }
}
