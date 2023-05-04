package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.RoomInfo;

public interface RoomInfoRepository extends JpaRepository<RoomInfo,Long> {
}
