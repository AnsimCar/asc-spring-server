package project.asc.AnsimCar.dto.address.response;

import lombok.Builder;
import lombok.Data;
import project.asc.AnsimCar.domain.Address;

@Data
public class AddressResponse {
    private Long id;

    private String sido;

    private String sigungu;

    private String eupmyeongdong;

    @Builder
    public AddressResponse(Long id, String sido, String sigungu, String eupmyeongdong) {
        this.id = id;
        this.sido = sido;
        this.sigungu = sigungu;
        this.eupmyeongdong = eupmyeongdong;
    }

    /**
     * 엔티티 -> RequestDto
     */
    public static AddressResponse from(Address entity) {
        return new AddressResponse(
                entity.getId(),
                entity.getSido(),
                entity.getSigungu(),
                entity.getEupmyeondong()
        );
    }

    /**
     * RequestDto -> 엔티티
     */
    public Address toEntity() {
        return Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .eupmyeondong(eupmyeongdong)
                .build();
    }
}
