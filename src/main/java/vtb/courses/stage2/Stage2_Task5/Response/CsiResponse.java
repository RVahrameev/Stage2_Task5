package vtb.courses.stage2.Stage2_Task5.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class CsiResponse {

    @Getter
    @Setter
    private CsiData data = new CsiData();
    @Getter
    @Setter
    private String errorMsg;

    public class CsiData {
        @Getter
        @Setter
        private int instanceId;
        @Getter
        @Setter
        private int[] registerId;
    }
}
