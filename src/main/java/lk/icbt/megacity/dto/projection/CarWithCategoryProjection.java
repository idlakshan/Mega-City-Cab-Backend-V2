package lk.icbt.megacity.dto.projection;

public interface CarWithCategoryProjection {
    Integer getCarId();
    String getCarName();
    String getCarNumber();
    String getCarImage();
    String getStatus();
    Integer getCategoryId();
    String getCategoryName();
}