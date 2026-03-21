package lk.icbt.megacity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private Integer driverId;
    private String driverName;
    private String driverNic;
    private String driverAddress;
    private String driverEmail;
    private String licenseImage;
    private String driverContact;
    private String status;
}
