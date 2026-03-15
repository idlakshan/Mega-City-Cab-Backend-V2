package lk.icbt.megacity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private int bookingId;
    private int userId;
    private int carId;
    private int driverId;
    private String pickupLocation;
    private String dropLocation;
    private LocalDateTime bookingDateTime;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String status;

    private CarDTO car;
    private DriverDTO driver;

    private PaymentDTO payment;
}
