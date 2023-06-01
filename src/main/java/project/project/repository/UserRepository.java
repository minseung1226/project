package project.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.project.domain.User;
import project.project.domain.enum_type.Option;
import project.project.domain.enum_type.UserJoinType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=:email and u.userJoinType=:userJoinType")
    public User findNormalByEmail(@Param("email") String email, @Param("userJoinType")UserJoinType userJoinType);

    @Query("select u from User u where u.kakaoId=:kakaoId and u.userJoinType=:userJoinType")
    public Optional<User> findByKakaoId(@Param("kakaoId")String kakaoId,@Param("userJoinType") UserJoinType userJoinType);

    public Optional<User> findByEmail(@Param("email")String email);
}
