package project.project.repository.roomrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.project.domain.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select r from Room r where r.user.id=:userId")
    List<Room> findRooms(@Param("userId")Long userId);
}
