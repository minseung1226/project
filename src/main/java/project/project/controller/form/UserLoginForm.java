package project.project.controller.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginForm {
    private String loginEmail;
    private String loginPw;

    public UserLoginForm(String loginEmail, String loginPw) {
        this.loginEmail = loginEmail;
        this.loginPw = loginPw;
    }
}
