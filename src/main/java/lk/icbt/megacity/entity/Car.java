package lk.icbt.megacity.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cars")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String carName;
    private String carNumber;
    private String carImage;
    private String status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
