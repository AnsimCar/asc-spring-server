package project.asc.AnsimCar.repository.querydsl.rent;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import project.asc.AnsimCar.domain.QRent;
import project.asc.AnsimCar.domain.QReview;
import project.asc.AnsimCar.domain.Rent;
import project.asc.AnsimCar.domain.Review;
import project.asc.AnsimCar.domain.type.CarCategory;
import project.asc.AnsimCar.domain.type.Fuel;
import project.asc.AnsimCar.domain.type.Status;
import project.asc.AnsimCar.dto.rent.request.RentSearchRequest;
import project.asc.AnsimCar.dto.rent.response.RentInfoResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static project.asc.AnsimCar.domain.QAccount.account;
import static project.asc.AnsimCar.domain.QAddress.address;
import static project.asc.AnsimCar.domain.QRent.rent;
import static project.asc.AnsimCar.domain.QReview.*;
import static project.asc.AnsimCar.domain.QUserCar.userCar;

@Repository
public class RentRepositoryCustomImpl implements RentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public RentRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Rent> findAllComplex(RentSearchRequest request, Pageable pageable) {
        List<Rent> result = jpaQueryFactory.selectFrom(rent)
                .leftJoin(rent.userCar, userCar)
                .fetchJoin()
                .leftJoin(rent.account, account)
                .fetchJoin()
                .leftJoin(rent.address, address)
                .fetchJoin()
                .where(rent.userCar.carModel.contains(request.getCarModel()),
                        carCategoryEq(request.getCarCategory()),
                        carFuelEq(request.getFuel()),
                        rent.address.sido.contains(request.getSido()),
                        rent.address.sigungu.contains(request.getSigungu()),
                        rent.address.eupmyeondong.contains(request.getEupmyeondong()),
                        rent.status.eq(Status.AVAILABLE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Rent> countQuery = jpaQueryFactory.selectFrom(rent)
                .where(rent.userCar.carModel.contains(request.getCarModel()),
                        carCategoryEq(request.getCarCategory()),
                        carFuelEq(request.getFuel()),
                        rent.address.sido.contains(request.getSido()),
                        rent.address.sigungu.contains(request.getSigungu()),
                        rent.address.eupmyeondong.contains(request.getEupmyeondong()),
                        rent.status.eq(Status.AVAILABLE));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<Rent> findNotReviewedByRentUserId(Long rentUserId, Pageable pageable) {
        List<Rent> result = jpaQueryFactory.selectFrom(rent)
                .leftJoin(rent.review, review)
                .fetchJoin()
                .leftJoin(rent.account, account)
                .fetchJoin()
                .leftJoin(rent.rentAccount, account)
                .fetchJoin()
                .leftJoin(rent.address, address)
                .fetchJoin()
                .leftJoin(rent.userCar, userCar)
                .fetchJoin()
                .where(rent.rentAccount.id.eq(rentUserId),
                        rent.status.eq(Status.RETURN),
                        rent.review.rate.eq(Expressions.nullExpression()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Rent> countQuery = jpaQueryFactory.selectFrom(rent)
                .where(rent.rentAccount.id.eq(rentUserId),
                        rent.status.eq(Status.RETURN),
                        rent.review.rate.eq(Expressions.nullExpression()));

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchCount);
    }

    private BooleanExpression carCategoryEq(CarCategory carCategory) {
        return carCategory != null ? rent.userCar.carCategory.eq(carCategory) : null;
    }

    private BooleanExpression carFuelEq(Fuel fuel) {
        return fuel != null ? rent.userCar.fuel.eq(fuel) : null;
    }

    @Override
    public Optional<Rent> findByRentAccountIdAndStatusOrStatus(Long rentAccountId, Status status1, Status status2) {

        Rent findRent = jpaQueryFactory.selectFrom(QRent.rent)
                .leftJoin(QRent.rent.review, review)
                .fetchJoin()
                .leftJoin(QRent.rent.account, account)
                .fetchJoin()
                .leftJoin(QRent.rent.rentAccount, account)
                .fetchJoin()
                .leftJoin(QRent.rent.address, address)
                .fetchJoin()
                .leftJoin(QRent.rent.userCar, userCar)
                .fetchJoin()
                .where(QRent.rent.rentAccount.id.eq(rentAccountId), QRent.rent.status.in(status1, status2)).fetchOne();

        return Optional.ofNullable(findRent);
    }
}
