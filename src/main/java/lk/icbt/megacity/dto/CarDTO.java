package lk.icbt.megacity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private int carId;
    private int categoryId;
    private String carName;
    private String carNumber;
    private String carImage;
    private String status;

}
