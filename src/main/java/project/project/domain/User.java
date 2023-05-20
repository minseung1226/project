package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;
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
    private UserStatus userStatus=UserStatus.일반;

    private String pimg;

    private String kakaoId;

    @Enumerated
    UserJoinType userJoinType;

    public User( String loginId, String name, String pw
            , String birth, String phone, Address address,UserStatus userStatus,String residentNumber,
                 String pimg,UserJoinType userJoinType) {
        this.email = loginId;
        this.name = name;
        this.pw = pw;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.userStatus=userStatus;
        this.residentNumber=residentNumber;
        this.pimg=pimg;
        this.userJoinType=userJoinType;
    }

    public User(String email, String name, String pw, UserJoinType userJoinType) {
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.userJoinType=userJoinType;
    }

    public User(String email, String birth, String pimg, String kakaoId, UserJoinType userJoinType) {
        this.email = email;
        this.birth = birth;
        this.pimg = pimg;
        this.kakaoId = kakaoId;
        this.userJoinType = userJoinType;
    }
}
