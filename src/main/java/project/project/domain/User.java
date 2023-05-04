package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@EqualsAndHashCode()
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

    @Enumerated
    private UserStatus userStatus;

    public User( String loginId, String name, String pw
            , String residentNumber, String phone, Address address,UserStatus userStatus) {
        this.loginId = loginId;
        this.name = name;
        this.pw = pw;
        this.residentNumber = residentNumber;
        this.phone = phone;
        this.address = address;
        this.userStatus=userStatus;
    }
}
