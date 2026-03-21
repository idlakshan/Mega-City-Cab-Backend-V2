package lk.icbt.megacity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCarRequestDTO {
    private Integer carId;
    private Integer categoryId;
    private String carName;
    private String carNumber;
    private MultipartFile carImage;
    private String status;
}
