package project.asc.AnsimCar.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.asc.AnsimCar.dto.rent.request.ImageRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(Long rentId, MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getSize());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        String path = "user/" + "rent/" + LocalDate.now() + "/" + rentId.toString() + "/" + "rent/" + "original/" +  s3FileName;

        amazonS3.putObject(bucket, path, multipartFile.getInputStream(), objMeta);

        return amazonS3.getUrl(bucket, path).toString();
    }
}
