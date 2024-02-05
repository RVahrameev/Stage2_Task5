package vtb.courses.stage2_task5.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.*;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Response.CsiResponse;
import vtb.courses.stage2_task5.Service.CsiServiceIntf;

import java.io.PrintWriter;
import java.io.StringWriter;

import static vtb.courses.stage2_task5.Request.JsonUtil.validateAndParseJson;

@RestController
public class CsiController {

    private CsiServiceIntf csiService;


    @PostMapping("corporate-settlement-instance/create/")
    public ResponseEntity<CsiResponse> createCsi(@RequestBody String requestJsonStr) {

        CsiResponse csiResponse;
        HttpStatus httpStatus;

        try {
            JsonSchema csiRequestJsonSchema = CreateCsiRequest.getJsonSchema();
            CreateCsiRequest csiRequest = validateAndParseJson(requestJsonStr, csiRequestJsonSchema, CreateCsiRequest.class);
            csiResponse = csiService.createCsi(csiRequest);
            httpStatus = HttpStatus.OK;
        }
        catch (JsonProcessingException | IllegalArgumentException e) {
            csiResponse = new CsiResponse();
            csiResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        catch (NoResultException e) {
            csiResponse = new CsiResponse();
            csiResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.NOT_FOUND;
        }
        catch (Exception e) {
            csiResponse = new CsiResponse();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            csiResponse.setErrorMsg(e.getMessage() +'\n'+ sw);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Возвращаем ответ на поступивший запрос
        return ResponseEntity.status(httpStatus).body(csiResponse);
    }

    @Autowired
    public void setCsiService(CsiServiceIntf csiService) {
        this.csiService = csiService;
    }
}
