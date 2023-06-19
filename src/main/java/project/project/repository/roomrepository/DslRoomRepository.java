package project.project.repository.roomrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import project.project.dto.RoomsDto;

import java.util.List;

@Repository

public class DslRoomRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public DslRoomRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<RoomsDto> findRooms(Long userId){

    }
}
