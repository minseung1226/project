package project.project.controller.form.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinForm {
    @NotEmpty
    private String name;
    @NotEmpty
    private String joinEmail;
    @NotEmpty
    private String joinPw;

    public UserJoinForm(String name, String joinEmail, String joinPw) {
        this.name = name;
        this.joinEmail = joinEmail;
        this.joinPw = joinPw;
    }
}
