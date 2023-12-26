package vtb.courses.stage2.Stage2_Task5.Request;

import lombok.Getter;
import lombok.Setter;

public class CreateCsiRequest {
    @Getter @Setter
    private int instanceId;
    @Getter @Setter
    private String registryTypeCode;

    @Override
    public String toString() {
        return "CreateCsiRequest{" +
                "instanceId=" + instanceId +
                ", registryTypeCode='" + registryTypeCode + '\'' +
                '}';
    }
}
