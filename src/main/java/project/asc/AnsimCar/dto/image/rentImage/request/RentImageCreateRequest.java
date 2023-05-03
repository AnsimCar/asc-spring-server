package project.asc.AnsimCar.dto.image.rentImage.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentImageCreateRequest {
    MultipartFile[] imgFiles;

    private RentImageCreateRequest() {
    }

    public RentImageCreateRequest(final MultipartFile[] imgFiles) {
        this.imgFiles = imgFiles;
    }

}
