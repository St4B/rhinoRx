package quadible.com.js;

public class DebugNeededException extends Throwable {

    private final String mScriptName;

    private final String mScript;

    private final String mMethod;

    private final Arguments mArguments;

    public DebugNeededException(String scriptName, String script, String method, Arguments arguments) {
        super("Requested debug for script: " + scriptName + ", method: " + method);
        mScriptName = scriptName;
        mScript = script;
        mMethod = method;
        mArguments = arguments;
    }

    public String getScriptName() {
        return mScriptName;
    }

    public String getScript() {
        return mScript;
    }

    public String getMethod() {
        return mMethod;
    }

    public Arguments getArguments() {
        return mArguments;
    }

}
