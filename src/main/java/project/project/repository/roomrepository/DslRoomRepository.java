package project.project.repository.roomrepository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import project.project.domain.QInquiry;
import project.project.domain.QRoom;
import project.project.domain.QWishlist;
import project.project.dto.QRoomSimpleDto;
import project.project.dto.RoomSimpleDto;

import java.util.List;


@Repository

public class DslRoomRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DslRoomRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RoomSimpleDto> findRooms(Long userId){

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
                                .where(inquiry.room.eq(room))))
                .from(room)
                .fetch();
    }
}
