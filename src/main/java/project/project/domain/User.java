package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project.domain.embeded.Address;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue
    private Long id;
    private String loginId;
    private String name;
    private String pw;
    private String residentNumber;
    private String phone;
    @Embedded
    private Address address;





}
