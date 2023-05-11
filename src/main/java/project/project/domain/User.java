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
    private String email;
    private String name;
    private String pw;
    private String birth;
    private String phone;
    @Embedded
    private Address address;

    private String residentNumber;

    @Enumerated
    private UserStatus userStatus;

    private String pimg;

    public User( String loginId, String name, String pw
            , String birth, String phone, Address address,UserStatus userStatus,String residentNumber,
                 String pimg) {
        this.email = loginId;
        this.name = name;
        this.pw = pw;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.userStatus=userStatus;
        this.residentNumber=residentNumber;
        this.pimg=pimg;
    }
}
