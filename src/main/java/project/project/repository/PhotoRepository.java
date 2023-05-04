package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
