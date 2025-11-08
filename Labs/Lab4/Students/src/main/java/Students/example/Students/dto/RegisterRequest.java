package Students.example.Students.dto;

import Students.example.Students.entity.User;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private User.Role role;
    private String code;
    private Integer year;
}
