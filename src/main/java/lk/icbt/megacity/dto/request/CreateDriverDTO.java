package lk.icbt.megacity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDriverDTO {
    private String driverName;
    private String driverNic;
    private String driverAddress;
    private String driverEmail;
    private String driverContact;
}
