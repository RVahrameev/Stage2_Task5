package vtb.courses.stage2.Stage2_Task5.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(csiRequest);
        CsiResponse csiResponse = csiService.createCsi(csiRequest);
        return ResponseEntity.status(csiResponse.getData().getResponseStatus()).body(csiResponse);
    }

}
