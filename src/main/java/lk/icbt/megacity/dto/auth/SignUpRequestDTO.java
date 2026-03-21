package lk.icbt.megacity.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {
    private String name;
    private String phone;
    private String nic;
    private String email;
    private String password;
}


