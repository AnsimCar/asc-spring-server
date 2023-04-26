package project.asc.AnsimCar.repository.querydsl.rent;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;

import java.util.List;

import static project.asc.AnsimCar.domain.QRent.rent;

@Repository
public class RentCustomRepositoryImpl implements RentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public RentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Rent> findAllComplex(RentSearchRequest request, Pageable pageable) {
        List<Rent> result = jpaQueryFactory.selectFrom(rent)
                .where(rent.userCar.carModel.contains(request.getCarModel()),
                        rent.userCar.carCategory.eq(request.getCarCategory()),
                        rent.userCar.fuel.eq(request.getFuel()),
                        rent.address.sido.contains(request.getSido()),
                        rent.address.sigungu.contains(request.getSigungu()),
                        rent.address.eupmyeondong.contains(request.getEupmyeondong()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Rent> countQuery = jpaQueryFactory.selectFrom(rent)
                .where(rent.userCar.carModel.contains(request.getCarModel()),
                        rent.userCar.carCategory.eq(request.getCarCategory()),
                        rent.userCar.fuel.eq(request.getFuel()),
                        rent.address.sido.contains(request.getSido()),
                        rent.address.sigungu.contains(request.getSigungu()),
                        rent.address.eupmyeondong.contains(request.getEupmyeondong()));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
    }
}
