package vtb.courses.stage2.Stage2_Task5.Response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class CsiResponse {

    @Getter
    @Setter
    private CsiData data = new CsiData();

    public class CsiData {
        @Setter
        private HttpStatus httpStatus;
        @Getter
        @Setter
        private int instanceId;
        @Getter
        @Setter
        private int[] registerId;

        public HttpStatus getResponseStatus() {
            return httpStatus;
        }
    }
}
