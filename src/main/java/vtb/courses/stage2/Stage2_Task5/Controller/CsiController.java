package vtb.courses.stage2.Stage2_Task5.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import vtb.courses.stage2.Stage2_Task5.Request.CreateCsiRequest;
import vtb.courses.stage2.Stage2_Task5.Response.CsiResponse;
import vtb.courses.stage2.Stage2_Task5.Service.CsiService;

@RestController
public class CsiController {

    @Autowired
    private CsiService csiService;

    @PostMapping("corporate-settlement-instance/create/")
    private ResponseEntity<CsiResponse> createCsi(
            @RequestBody CreateCsiRequest csiRequest) {
        HttpStatus httpStatus = HttpStatus.OK;
        CsiResponse csiResponse;
        try {
            csiResponse = csiService.createCsi(csiRequest);
        } catch (Exception e) {
            csiResponse = new CsiResponse();
            csiResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(csiResponse);
    }

}
