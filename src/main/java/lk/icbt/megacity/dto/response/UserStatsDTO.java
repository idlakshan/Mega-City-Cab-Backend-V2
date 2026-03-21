package lk.icbt.megacity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsDTO {
    private int totalRides;
    private double totalSpending;
    private String activeSince;
    private String favoriteLocation;
}
