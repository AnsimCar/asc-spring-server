package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.AfterImage;
import project.asc.AnsimCar.domain.BeforeImage;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.exception.rent.RentNotFoundException;
import project.asc.AnsimCar.repository.AfterImageRepository;
import project.asc.AnsimCar.repository.RentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AfterImageService {
    private final AfterImageRepository afterImageRepository;
    private final RentRepository rentRepository;

    /**
     * 사진 저장
     */
    public void add(Long rendId, String imageFront, String imageRear, String imageRight, String imageLeft) {
        Rent rent = rentRepository.findById(rendId).orElseThrow(RentNotFoundException::new);

        AfterImage afterImage = AfterImage.builder()
                .rent(rent)
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();

        afterImageRepository.save(afterImage);
    }
}
