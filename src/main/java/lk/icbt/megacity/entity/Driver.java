package lk.icbt.megacity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;
    private String driverName;
    private String driverNic;
    private String driverAddress;
    private String driverEmail;
    private String licenseImage;
    private String driverContact;
    private String status;
}