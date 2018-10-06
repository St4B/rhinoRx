package quadible.com.js;

public class ScriptLoader {

    public static final String sFunctionName = "propertyChange";

    private final String mScriptName = "MyScript";

    private final String mScript =
            "debugger;"
                    + "function " + sFunctionName + "(model) {"
                    + "model.put('loco', 'modo');"
                    + "model.put('active', 'member');"
                    + "showDialog('koukou!');"
                    + "showDialog('Second', 'koukou!');"
                    + "showDialog('Third', 'koukou!', 'Ok');"
                    + "showDialog('Fourth', 'koukou!', 'Ok', 'Cancel');"
                    + "var data = query('select * from somewhere');"
                    + "model.put(data[0][0], data[0][1]); "
                    + "model.put(data[1][0], data[1][1]); "
                    + "return true;"
                    + "}";

    public String load(String scriptName) {
        return mScript;
    }

    public void save(String scriptName, String script) {
        //todo
    }

}
