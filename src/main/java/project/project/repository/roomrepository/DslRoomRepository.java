package project.project.repository.roomrepository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QList;
import com.querydsl.core.types.dsl.ListExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.project.domain.*;
import project.project.dto.photo.PhotoDto;
import project.project.dto.photo.QPhotoDto;
import project.project.dto.room.QRoomModifyDto;
import project.project.dto.room.QRoomSimpleDto;
import project.project.dto.room.RoomModifyDto;
import project.project.dto.room.RoomSimpleDto;

import java.util.List;

import static project.project.domain.QPhoto.*;
import static project.project.domain.QRoom.*;
import static project.project.domain.QRoomInfo.*;


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
                .fetch();
    }


}
