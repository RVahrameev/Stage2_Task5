package vtb.courses.stage2_task5.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vtb.courses.stage2_task5.Request.CreateAccountRequest;
import vtb.courses.stage2_task5.Response.CreateAccountResponse;
import vtb.courses.stage2_task5.Service.CreateAccountServiceIntf;

import java.io.PrintWriter;
import java.io.StringWriter;

import static vtb.courses.stage2_task5.Request.JsonSchemaUtil.validateAndParseJson;

@RestController
public class AccountController {

    private CreateAccountServiceIntf accountService;

    @PostMapping("corporate-settlement-account/create/")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody String requestJsonStr) {
        CreateAccountResponse accountResponse;
        HttpStatus httpStatus;
        try {
            CreateAccountRequest accountRequest = validateAndParseJson(requestJsonStr, CreateAccountRequest.getJsonSchema(), CreateAccountRequest.class);
            accountResponse = accountService.createAccount(accountRequest);
            httpStatus = HttpStatus.OK;
        }
        catch (JsonProcessingException | IllegalArgumentException e) {
            accountResponse = new CreateAccountResponse();
            accountResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        catch (NoResultException e) {
            accountResponse = new CreateAccountResponse();
            accountResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.NOT_FOUND;
        }
        catch (Exception e) {
            accountResponse = new CreateAccountResponse();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            accountResponse.setErrorMsg(e.getMessage() +'\n'+ sw);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Возвращаем ответ на поступивший запрос
        return ResponseEntity.status(httpStatus).body(accountResponse);

    }

    @Autowired
    public void setAccountService(CreateAccountServiceIntf accountService) {
        this.accountService = accountService;
    }
}
