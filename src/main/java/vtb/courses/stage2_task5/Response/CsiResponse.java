package vtb.courses.stage2_task5.Response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class CsiResponse {

    @Getter
    @Setter
    private CsiData data = new CsiData();
    @Getter
    @Setter
    private String errorMsg;

    @ToString
    public class CsiData {
        @Getter
        @Setter
        private int instanceId;
        @Getter
        @Setter
        private List<Integer> registerId = new ArrayList<>();
        @Getter
        @Setter
        private List<Integer> supplementaryAgreementId = new ArrayList<>();
    }
}
