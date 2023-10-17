package project.project.repository.roomrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.project.domain.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("select r from Room r where r.user.id=:userId")
    List<Room> findRooms(@Param("userId")Long userId);

    @Query("select r from Room r join fetch r.user join fetch r.roomInfo join fetch r.photos where r.id=:roomId")
    Room fetchFindById(@Param("roomId") Long roomId);

    @Query("select r from Room r join fetch r.roomInfo join fetch r.photos where r.lng>=:minLng and " +
            "r.lng<=:maxLng and r.lat>=:minLat and r.lat<=:maxLat")
    List<Room> findByPosition(Double minLng,Double minLat, Double maxLng,Double maxLat );

    @Query("select r from Room r join fetch r.roomInfo join fetch r.photos where r.user.id=:userId")
    List<Room> findByUserId(Long userId);

    @Query("select r from Room r join fetch r.roomInfo join fetch r.photos where r.user.id in(:userIds)")
    List<Room> findRoomsByIds(@Param("userIds")Long[] userIds);
}
