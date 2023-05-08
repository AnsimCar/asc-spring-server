package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.ReturnImage;
import project.asc.AnsimCar.dto.image.returnImage.response.ReturnImageResponse;
import project.asc.AnsimCar.exception.rent.RentNotFoundException;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.repository.ReturnImageRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnImageService {
    private final ReturnImageRepository returnImageRepository;
    private final RentRepository rentRepository;

    /**
     * 사진 저장
     */
    public void add(Long rendId, String imageFront, String imageRear, String imageRight, String imageLeft) {
        Rent rent = rentRepository.findById(rendId).orElseThrow(RentNotFoundException::new);

        ReturnImage returnImage = ReturnImage.builder()
                .rent(rent)
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();

        returnImageRepository.save(returnImage);
    }

    /**
     * 렌트 아이디로 조회
     */
    public ReturnImageResponse findByRentId(final Long id) {
        return ReturnImageResponse.from(returnImageRepository.findByRent_Id(id).get());
    }
}
