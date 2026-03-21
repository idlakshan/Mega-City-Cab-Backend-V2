package lk.icbt.megacity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignedDriverDTO {

    private Integer driverId;
    private String driverName;
    private String driverContact;
    private String licenseImage;

}
