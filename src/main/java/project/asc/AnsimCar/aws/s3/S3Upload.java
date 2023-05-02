package project.asc.AnsimCar.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.asc.AnsimCar.dto.rent.request.ImageRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3Upload {
    public static String FLASK_URL = "http://127.0.0.1:5000";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String upload(Long accountId, Long rentId, MultipartFile multipartFile, String loc) throws IOException {
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());
        objMeta.setContentLength(multipartFile.getSize());
        objMeta.setContentLength(multipartFile.getInputStream().available());

        String path = accountId + "/" + "rent/" + LocalDate.now() + "/" + rentId.toString() + "/" + "rent/" + "original/" + loc;

        amazonS3.putObject(bucket, path, multipartFile.getInputStream(), objMeta);

        return imageRender(amazonS3.getUrl(bucket, path).toString());
    }

    public String imageRender(String imgUrl) {
        String url = FLASK_URL + "/convert/" + imgUrl;
        String sb = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb += line;
            }

            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb;
    }
}
