package quadible.com.js;

import java.util.HashMap;

public class ModelToArgumentsMapper {

    public Arguments transform(Model model) {
        return new Arguments.Builder()
                .putValue("active", model.getActive())
                .putValue("loco", model.getLoco())
                .trigger("onCreate()")
                .build();
    }

    public Model transform(ScriptingResult result) {
        HashMap<String, String> values = result.getValues();

        return new Model.Builder()
                .setActive(values.get("active"))
                .setLoco(values.get("loco"))
                .build();
    }
}
