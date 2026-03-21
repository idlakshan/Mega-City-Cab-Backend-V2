package lk.icbt.megacity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingAssignmentResponseDTO {
    private AssignedCarDTO car;
    private AssignedDriverDTO driver;
}
