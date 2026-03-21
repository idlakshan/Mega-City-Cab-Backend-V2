package lk.icbt.megacity.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignedCarDTO {

    private Integer carId;
    private String carName;
    private String carNumber;
    private String carImage;

}