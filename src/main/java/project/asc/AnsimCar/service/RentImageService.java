package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.RentImage;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.exception.rent.RentNotFoundException;
import project.asc.AnsimCar.repository.RentImageRepository;
import project.asc.AnsimCar.repository.RentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RentImageService {
    private final RentImageRepository rentImageRepository;
    private final RentRepository rentRepository;

    /**
     * 사진 저장
     */
    public void add(Long rendId, String imageFront, String imageRear, String imageRight, String imageLeft) {
        Rent rent = rentRepository.findById(rendId).orElseThrow(RentNotFoundException::new);

        RentImage rentImage = RentImage.builder()
                .rent(rent)
                .imageFront(imageFront)
                .imageRear(imageRear)
                .imageRight(imageRight)
                .imageLeft(imageLeft)
                .build();

        rentImageRepository.save(rentImage);
    }
}
