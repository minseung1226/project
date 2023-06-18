package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {

}
