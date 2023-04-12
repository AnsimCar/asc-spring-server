package project.asc.AnsimCar.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.asc.AnsimCar.common.annotation.ServiceTest;
import project.asc.AnsimCar.common.fixture.RentFixture;
import project.asc.AnsimCar.domain.Account;
import project.asc.AnsimCar.domain.Address;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.UserCar;
import project.asc.AnsimCar.dto.rent.request.RentUpdateRequest;
import project.asc.AnsimCar.dto.rent.response.RentResponse;
import project.asc.AnsimCar.repository.AccountRepository;
import project.asc.AnsimCar.repository.AddressRepository;
import project.asc.AnsimCar.repository.RentRepository;
import project.asc.AnsimCar.repository.UserCarRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static project.asc.AnsimCar.common.fixture.AccountFixture.createAccount;
import static project.asc.AnsimCar.common.fixture.AddressFixture.createAddress;
import static project.asc.AnsimCar.common.fixture.AddressFixture.createAddressCreateRequest;
import static project.asc.AnsimCar.common.fixture.UserCarFixture.createUserCar1;


class RentServiceTest extends ServiceTest {

    @Autowired AccountRepository accountRepository;
    @Autowired UserCarRepository userCarRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired RentRepository rentRepository;

    @Autowired RentService rentService;


    @Test
    @DisplayName("렌트를_저장한다&계정Id로_렌트를_조회한다")
    void 렌트를_저장한다_그리고_계정Id로_렌트를_조회한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);
        UserCar userCar = userCarRepository.save(createUserCar1(account));

        //when
        rentService.addRent(account.getId(), userCar.getId(), createAddressCreateRequest());
        List<RentResponse> rentResponses = rentService.findByUserId(account.getId());

        //then
        assertThat(rentResponses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("렌트_상태를_수정한다")
    void 렌트_상태를_수정한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        UserCar userCar = userCarRepository.save(createUserCar1(account));

        Address address = addressRepository.save(createAddress());

        Rent rent = RentFixture.createRent(userCar, account, address);
        rentRepository.save(rent);

        RentUpdateRequest rentUpdateRequest = RentFixture.createRentUpdateRequest1();


        //when
        rentService.updateRentStatus(account.getId(), rent.getId(), rentUpdateRequest);


        //then
        Rent updateRent = rentRepository.findById(rent.getId()).get();
        assertThat(updateRent.getStatus()).isEqualTo(rentUpdateRequest.getStatus());
    }

    @Test
    @DisplayName("렌트_대여_날짜_반납_날짜를_수정한다")
    void 렌트_대여_반납_날짜를_수정한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        UserCar userCar = userCarRepository.save(createUserCar1(account));

        Address address = addressRepository.save(createAddress());

        Rent rent = RentFixture.createRent(userCar, account, address);
        rentRepository.save(rent);

        RentUpdateRequest rentUpdateRequest = RentFixture.createRentUpdateRequest2();


        //when
        rentService.updateRentalReturnDate(account.getId(), rent.getId(), rentUpdateRequest);

        //then
        Rent updateRent = rentRepository.findById(rent.getId()).get();
        assertThat(updateRent.getRentalDate()).isEqualTo(rentUpdateRequest.getRentalDate());
        assertThat(updateRent.getReturnDate()).isEqualTo(rentUpdateRequest.getReturnDate());
    }

    @Test
    @DisplayName("렌트를_삭제한다")
    void 렌트를_삭제한다() {
        //given
        Account account = createAccount();
        accountRepository.save(account);

        UserCar userCar = userCarRepository.save(createUserCar1(account));

        Address address = addressRepository.save(createAddress());

        Rent rent = RentFixture.createRent(userCar, account, address);
        rentRepository.save(rent);


        //when
        rentService.deleteRent(account.getId(), rent.getId());

        //then
        Optional<Rent> nonExistentRent = rentRepository.findById(rent.getId());

        assertThat(nonExistentRent).isEmpty();
    }
}