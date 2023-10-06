package project.project.controller.form.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.User;
import project.project.domain.embeded.Address;
import project.project.domain.enum_type.UserJoinType;

@Data
@NoArgsConstructor
public class UserModifyForm {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private MultipartFile pimg;

    private String pimgString;
    private Integer postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
    private String birthY;
    private String birthM;
    private String birthD;

    private UserJoinType userJoinType;

    public UserModifyForm(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.pimgString = user.getPimg();
        this.postcode = user.getAddress().getPostcode();
        this.address = user.getAddress().getAddress();
        this.detailAddress = user.getAddress().getDetailAddress();
        this.extraAddress = user.getAddress().getExtraAddress();
        userJoinType=user.getUserJoinType();


        if(user.getBirth()!=null && !user.getBirth().isEmpty()){
            this.birthY = user.getBirth().substring(0,4);
            this.birthM = user.getBirth().substring(4,6);
            this.birthD = user.getBirth().substring(6,8);

        }
    }
}
