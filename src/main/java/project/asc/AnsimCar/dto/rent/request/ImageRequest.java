package project.asc.AnsimCar.dto.rent.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequest {

    private MultipartFile carFront;     //정면
    private MultipartFile carRear;      //후면
    private MultipartFile carLeft;      //좌측
    private MultipartFile carRight;     //우측
}
