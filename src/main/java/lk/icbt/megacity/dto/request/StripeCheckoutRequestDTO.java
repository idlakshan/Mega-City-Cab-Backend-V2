package lk.icbt.megacity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StripeCheckoutRequestDTO {
    private double amount;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private Integer userId;
    private Integer carId;
    private Integer driverId;
    private String pickupLocation;
    private String dropLocation;
    private LocalDateTime bookingDateTime;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
