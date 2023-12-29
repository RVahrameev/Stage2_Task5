package vtb.courses.stage2.Stage2_Task5.Response;

import lombok.Getter;
import lombok.Setter;

public class CreateAccountResponse {

    @Getter
    @Setter
    private CsiData data;// = new CsiData();
    @Getter
    @Setter
    private String errorMsg;

    public class CsiData {
        @Getter
        @Setter
        private String accountId;
    }
}
