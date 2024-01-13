package vtb.courses.stage2_task5.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.*;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import vtb.courses.stage2_task5.Request.CreateAccountRequest;
import vtb.courses.stage2_task5.Request.CreateCsiRequest;
import vtb.courses.stage2_task5.Response.CreateAccountResponse;
import vtb.courses.stage2_task5.Response.CsiResponse;
import vtb.courses.stage2_task5.Service.CsiService;
import vtb.courses.stage2_task5.Service.CreateAccountService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Set;

@RestController
public class CsiController {

    private CsiService csiService;
    private CreateAccountService accountService;

    private <T> T validateAndParseJson(String jsonStr, JsonSchema jsonSchema, Class<T> objClass)
            throws JsonProcessingException, IllegalArgumentException
    {
        // Поскольку мы решили валидировать json, то он к нам приходит неразборанным
        // поэтому нам нужен маппер, который его переведёт в структуру нашего объекта
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        // Валидируем полученный json
        Set<ValidationMessage> validateErorrs = jsonSchema.validate(objectMapper.readTree(jsonStr));

        // Если входящий json прошёл проверку создаем входящий объект и возвращаем его
        if (validateErorrs.isEmpty()) {
            return objectMapper.readValue(jsonStr, objClass);
        } else {
            // Найденные ошибки собираем и генерируем с ними исключение
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ошибки разбора входящего json\n");
            for (ValidationMessage error: validateErorrs) {
                stringBuilder.append(error);
                stringBuilder.append('\n');
            }
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @PostMapping("corporate-settlement-instance/create/")
    private ResponseEntity<CsiResponse> createCsi(@RequestBody String requestJsonStr) {

        CsiResponse csiResponse;
        HttpStatus httpStatus;

        try {
            CreateCsiRequest csiRequest = validateAndParseJson(requestJsonStr, CreateCsiRequest.getJsonSchema(), CreateCsiRequest.class);
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

    @PostMapping("corporate-settlement-account/create/")
    private ResponseEntity<CreateAccountResponse> createAccount(@RequestBody String requestJsonStr) {
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
    public void setCsiService(CsiService csiService) {
        this.csiService = csiService;
    }

    @Autowired
    public void setAccountService(CreateAccountService accountService) {
        this.accountService = accountService;
    }
}
