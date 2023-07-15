package project.project.repository.roomrepository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QList;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ListExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.project.domain.*;
import project.project.domain.enum_type.EntityStatus;
import project.project.dto.photo.PhotoDto;
import project.project.dto.photo.QPhotoDto;
import project.project.dto.room.*;
import project.project.dto.roominfo.QRoomInfoModifyDto;
import project.project.search.RoomSearchParameters;

import java.util.List;

import static project.project.domain.QPhoto.*;
import static project.project.domain.QRoom.*;
import static project.project.domain.QRoomInfo.*;
import static project.project.domain.QUser.*;


@Repository

public class DslRoomRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DslRoomRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RoomSimpleDto> findRoomDtos(Long userId){

        QRoom room = QRoom.room;
        QInquiry inquiry = QInquiry.inquiry;
        QWishlist wishlist = QWishlist.wishlist;

        return queryFactory
                .select(new QRoomSimpleDto(
                        room.id,
                        room.roomNumber,
                        room.address.address,
                        room.status,
                        room.createDate,
                        JPAExpressions
                                .select(wishlist.count())
                                .from(wishlist)
                                .where(wishlist.room.eq(room)),
                        JPAExpressions
                                .select(inquiry.count())
                                .from(inquiry)
                                .where(inquiry.room.eq(room)),
                        room.deposit,
                        room.monthlyRent))
                .from(room)
                .leftJoin(room.user,user)
                .where(user.id.eq(userId).and(room.entityStatus.eq(EntityStatus.BASIC)))
                .fetch();
    }

    public RoomModifyDto findRoomDto(Long roomId) {
        RoomModifyDto roomModifyDto = queryFactory.select(new QRoomModifyDto(
                        room.id,
                        room.user.id,
                        room.roomNumber,
                        room.registrant,
                        room.houseType,
                        room.roomType,
                        room.address.postcode,
                        room.address.address,
                        room.address.detailAddress,
                        room.address.extraAddress,
                        room.lat,
                        room.lng,
                        room.deposit,
                        room.monthlyRent,
                        room.maintenance,
                        room.moveInDate,
                        room.maintenanceItem,
                        room.title,
                        room.content,
                        new QRoomInfoModifyDto(
                                roomInfo.bearing,
                                roomInfo.options,
                                roomInfo.animal,
                                roomInfo.parking,
                                roomInfo.floor,
                                roomInfo.entireFloor,
                                roomInfo.realSize,
                                roomInfo.supplySize)))
                .from(room)
                .join(room.user, user)
                .join(room.roomInfo,roomInfo)
                .where(room.id.eq(roomId).and(room.entityStatus.eq(EntityStatus.BASIC)))
                .fetchOne();

        List<String> imgNames = queryFactory.select(photo.img)
                .from(photo)
                .where(photo.room.id.eq(roomId)).fetch();

        roomModifyDto.setImg(imgNames);

        return roomModifyDto;
    }


    public List<Room> roomSearch(RoomSearchParameters roomSearchParameters){
        return queryFactory.selectFrom(room)
                .join(room.roomInfo,roomInfo).fetchJoin()
                .join(room.photos,photo).fetchJoin()
                .where(createDepositRangeCondition(roomSearchParameters.getMinDeposit(),roomSearchParameters.getMaxDeposit()),
                        createMonthlyRentRangeCondition(roomSearchParameters.getMinMonthlyRent(),roomSearchParameters.getMaxMonthlyRent()),
                        createRealSizeRangeCondition(roomSearchParameters.getMinRealSize(),roomSearchParameters.getMaxRealSize()),
                        createCoordinatesRangeCondition(roomSearchParameters.getMinLat(),roomSearchParameters.getMaxLat(),
                                                        roomSearchParameters.getMinLng(),roomSearchParameters.getMaxLng()))
                .fetch();
    }

    private BooleanExpression createDepositRangeCondition(Integer minDeposit,Integer maxDeposit){
        BooleanExpression be;

        be=minDeposit==null?null:room.deposit.goe(minDeposit);
        return maxDeposit==null?be:be.and(room.deposit.loe(maxDeposit));
    }

    private BooleanExpression createMonthlyRentRangeCondition(Integer minMonthlyRent,Integer maxMonthlyRent){
        BooleanExpression be;

        be=minMonthlyRent==null?null:room.monthlyRent.goe(minMonthlyRent);

        return maxMonthlyRent==null?be:be.and(room.monthlyRent.loe(maxMonthlyRent));
    }

    private BooleanExpression createRealSizeRangeCondition(Integer minRealSize,Integer maxRealSize){

        BooleanExpression be;

        be=minRealSize==null?null:roomInfo.realSize.goe((double)minRealSize/0.3025);
        return maxRealSize==null?be:roomInfo.realSize.loe((double)maxRealSize/0.3025);
    }

    private BooleanExpression createCoordinatesRangeCondition(Double minLat,Double maxLat,
                                                              Double minLng,Double maxLng){
        System.out.println("lat="+minLat+","+maxLat);
        System.out.println("lng="+minLng+","+maxLng);
        return room.lat.goe(minLat).and(room.lat.loe(maxLat).and(room.lng.goe(minLng).and(room.lng.loe(maxLng))));
    }


}
