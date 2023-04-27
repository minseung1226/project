package project.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;
    private String loginId;
    private String name;
    private String pw;


}
