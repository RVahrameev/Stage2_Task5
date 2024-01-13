package vtb.courses.stage2_task5.Request;

import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SchemaValidatorsConfig;
import com.networknt.schema.SpecVersion;

import java.util.Locale;

public class JsonSchemaUtil {
    protected static com.networknt.schema.JsonSchema getJsonSchema(Class requestClass){
        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
        config.setLocale(new Locale("ru", "RU"));
        com.networknt.schema.JsonSchema jsonSchema =
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7)
                        .getSchema(
                                ClassLoader.getSystemResourceAsStream("json-schema/"+requestClass.getSimpleName() + ".json"),
                                config
                        );
        return jsonSchema;
    }

}
