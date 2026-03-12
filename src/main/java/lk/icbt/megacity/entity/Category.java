package lk.icbt.megacity.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String icon;
    private String title;
    @Column(columnDefinition = "json")
    private String features;
    private double price;
}
