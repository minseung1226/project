package project.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import project.project.domain.baseentity.BaseEntity;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;
import project.project.domain.enum_type.UserStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@EqualsAndHashCode()
public class User extends BaseEntity {
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

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus=UserStatus.일반;

    private String pimg;

    private String kakaoId;

    @Enumerated(value = EnumType.STRING)
    UserJoinType userJoinType=UserJoinType.NORMAR;

    public User(String email, String name, String pw) {
        this.email = email;
        this.name = name;
        this.pw = pw;
    }

    public User(String email, String birth, String pimg, String kakaoId, UserJoinType userJoinType) {
        this.email = email;
        this.birth = birth;
        this.pimg = pimg;
        this.kakaoId = kakaoId;
        this.userJoinType = userJoinType;
    }

    public User(String email, String name, String pw, String birth, String phone, Address address, String residentNumber, UserStatus userStatus, UserJoinType userJoinType) {
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.residentNumber = residentNumber;
        this.userStatus = userStatus;
        this.userJoinType = userJoinType;
    }

    public User(String email, String name, String pw, String phone, Address address) {
        this.email = email;
        this.name = name;
        this.pw = pw;
        this.phone = phone;
        this.address = address;
    }

    public void changePw(String pw){
        this.pw=pw;
    }
    public void changeAddress(Address address){
        this.address=address;
    }

    public void modifyUser(String name,String phone,String email,String pimg,Address address){
        if (StringUtils.hasText(name)) this.name = name;
        if(StringUtils.hasText(phone))this.phone=phone;
        if(StringUtils.hasText(email))this.email=email;
        if(StringUtils.hasText(pimg))this.pimg=pimg;
        if(address!=null)this.address=address;
    }


}
