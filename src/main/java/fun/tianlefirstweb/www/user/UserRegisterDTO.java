package fun.tianlefirstweb.www.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterDTO {

    @Pattern(regexp = "^[a-zA-Z0-9._-]{4,14}$", message = "用户名是由a～z的英文字母、0～9的数字、点、减号或下划线组成，长度为4～14个字符")
    private String username;

    private String password;

    @Email(message = "电子邮件地址不合法")
    private String email;
}
