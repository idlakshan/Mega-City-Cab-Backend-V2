package lk.icbt.megacity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private int paymentId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private String paymentStatus;
    private Timestamp paymentDate;
}
