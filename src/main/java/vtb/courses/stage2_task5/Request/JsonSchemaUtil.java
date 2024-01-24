package vtb.courses.stage2_task5.Request;

import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaValidatorsConfig;
import com.networknt.schema.SpecVersion;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class JsonSchemaUtil {

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

}
