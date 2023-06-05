package project.project.controller.form;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.project.domain.embeded.Address;

@Data
public class UserModifyForm {

    private Long id;
    private String name;
    private String tel;
    private String email;
    private MultipartFile pimg;
    private Integer postcode;
    private String address;
    private String detailAddress;
    private String extraAddress;
}
