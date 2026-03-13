package lk.icbt.megacity.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUtil {
    private int status;
    private String message;
    private Object data;

    public ResponseUtil(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseUtil(int status,Object data){
        this.status = status;
        this.data = data;
    }
}
