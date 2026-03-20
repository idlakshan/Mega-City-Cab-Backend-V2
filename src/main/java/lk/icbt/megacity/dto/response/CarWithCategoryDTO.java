package lk.icbt.megacity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarWithCategoryDTO {
    private Integer carId;
    private String carName;
    private String carNumber;
    private String carImage;
    private String status;

    private Integer categoryId;
    private String categoryName;
}
