package vtb.courses.stage2.Stage2_Task5.Service;

import org.springframework.http.HttpStatus;
import vtb.courses.stage2.Stage2_Task5.Request.CreateCsiRequest;
import vtb.courses.stage2.Stage2_Task5.Response.CsiResponse;

public class CsiService {
    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();
        csiResponse.getData().setHttpStatus(HttpStatus.BAD_REQUEST);
        csiResponse.getData().setInstanceId(777);
        csiResponse.getData().setRegisterId(new int[]{111,222,333});
        return csiResponse;
    };
}
