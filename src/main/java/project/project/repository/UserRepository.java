package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project.domain.User;
import project.project.domain.enum_type.Option;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    public Optional<User> findByKakaoId(String kakaoId);
}
