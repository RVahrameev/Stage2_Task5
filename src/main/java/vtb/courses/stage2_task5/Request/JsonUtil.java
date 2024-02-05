package vtb.courses.stage2_task5.Request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Set;

public class JsonUtil {

    public static String fileToString(String fileName) throws IOException {
        InputStream jsonStream = ClassLoader.getSystemResourceAsStream("BOOT-INF/classes/"+fileName);
        if (jsonStream == null) {
            jsonStream = ClassLoader.getSystemResourceAsStream(fileName);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
        String line;
        StringBuilder text = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            text.append(line);
        }
        reader.close();

        return text.toString();
    }

    protected static com.networknt.schema.JsonSchema getJsonSchema(Class requestClass) {
        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
        config.setLocale(new Locale("ru", "RU"));
        String jsonSchemaString = "";
        try {
            jsonSchemaString = fileToString("json-schema/" + requestClass.getSimpleName() + ".json");
        } catch (IOException e) {
            System.out.println("Error loading jsonSchema for "+ requestClass.getSimpleName());
        }
        com.networknt.schema.JsonSchema jsonSchema =
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
                        .getSchema(jsonSchemaString, config);
        return jsonSchema;
    }

    public  static <T> T validateAndParseJson(String jsonStr, JsonSchema jsonSchema, Class<T> objClass)
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

}
