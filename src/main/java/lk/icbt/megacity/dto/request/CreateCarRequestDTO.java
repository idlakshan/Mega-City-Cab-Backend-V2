package lk.icbt.megacity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequestDTO {
    private int categoryId;
    private String carName;
    private String carNumber;
    private MultipartFile carImage;
}
