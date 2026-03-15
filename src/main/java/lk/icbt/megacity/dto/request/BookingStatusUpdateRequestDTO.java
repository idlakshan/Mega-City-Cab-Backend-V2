package lk.icbt.megacity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingStatusUpdateRequestDTO {
    @NotNull(message = "Booking id is required")
    private Integer bookingId;

    @NotBlank(message = "Status is required")
    private String status;
}
