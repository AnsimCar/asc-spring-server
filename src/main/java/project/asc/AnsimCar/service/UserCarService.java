package project.asc.AnsimCar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.dto.usercar.UserCarDto;
import project.asc.AnsimCar.dto.usercar.UserCarRequest;
import project.asc.AnsimCar.exception.UserCarNotFoundException;
import project.asc.AnsimCar.repository.UserCarRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCarService {
    private UserCarRepository userCarRepository;

    /**
     * 차량 등록
     */
    public void save(UserCarRequest userCarRequest) {
        userCarRepository.save(userCarRequest.toEntity());
    }

    /**
     * ID 검색
     */
    public UserCarDto findById(Long id) {
        UserCar userCar = userCarRepository.findById(id).orElseThrow(() -> new UserCarNotFoundException());
        return UserCarDto.from(userCar);
    }

    /**
     * 모델명 검색
     */
    public List<UserCarDto> findByCarModel(String carModel) {
        List<UserCar> userCars = userCarRepository.findByCarModel(carModel);
        if (userCars.isEmpty())
            new UserCarNotFoundException();

        List<UserCarDto> userCarDtoList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarDtoList.add(UserCarDto.from(userCar));
        }

        return userCarDtoList;
    }

    /**
     * 차종 검색
     */
    public List<UserCarDto> findByCarCategory(CarCategory carCategory) {
        List<UserCar> userCars = userCarRepository.findByCarCategory(carCategory.getDescription());
        if (userCars.isEmpty())
            new UserCarNotFoundException();

        List<UserCarDto> userCarDtoList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarDtoList.add(UserCarDto.from(userCar));
        }

        return userCarDtoList;
    }

    /**
     * 제조사 검색
     */
    public List<UserCarDto> findByCarCategory(String manufacturer) {
        List<UserCar> userCars = userCarRepository.findByManufacturer(manufacturer);
        if (userCars.isEmpty())
            new UserCarNotFoundException();

        List<UserCarDto> userCarDtoList = new ArrayList<>();

        for (UserCar userCar : userCars) {
            userCarDtoList.add(UserCarDto.from(userCar));
        }

        return userCarDtoList;
    }

    /**
     * 차량 업데이트
     */
    public UserCarDto updateUserCar(UserCarRequest userCarRequest) {
        UserCar userCar = userCarRepository.findById(userCarRequest.toEntity().getId()).orElseThrow(() -> new UserCarNotFoundException());
        userCar.setUserCar(userCarRequest.toEntity());
        userCarRepository.save(userCar);

        return UserCarDto.from(userCar);
    }

    /**
     * 차량 삭제
     */
    public Boolean deleteById(UserCarRequest userCarRequest) {
        UserCar userCar = userCarRepository.findById(userCarRequest.toEntity().getId()).orElseThrow(() -> new UserCarNotFoundException());
        userCarRepository.delete(userCar);

        return true;
    }
}
