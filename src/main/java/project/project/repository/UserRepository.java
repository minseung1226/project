package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
