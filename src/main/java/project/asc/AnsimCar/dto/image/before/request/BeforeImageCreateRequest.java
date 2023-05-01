package project.asc.AnsimCar.dto.image.before.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.asc.AnsimCar.domain.BeforeImage;

import javax.validation.constraints.NotBlank;

@Data
public class BeforeImageCreateRequest {
    MultipartFile[] imgFiles;

    private BeforeImageCreateRequest() {
    }

    public BeforeImageCreateRequest(final MultipartFile[] imgFiles) {
        this.imgFiles = imgFiles;
    }

}
