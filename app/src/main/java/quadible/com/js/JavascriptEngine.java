package quadible.com.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Single;

public class JavascriptEngine {

    private final String mScriptName;

    private final String mFunction;

    private final String mScript;

    private final IQueryExecutor mQueryExecutor;

    public JavascriptEngine(
            String scriptName, String script, String functionName, IQueryExecutor queryExecutor) {
        mScriptName = scriptName;
        mScript = addStandardMethods(script);
        mFunction = functionName;
        mQueryExecutor = queryExecutor;
    }

    public Single<ScriptingResult> execute(Arguments arguments) {
        return Single.fromCallable(() -> executeImp(arguments));
    }

    private ScriptingResult executeImp(Arguments arguments) {
        Context cx = Context.enter();

        try {
            //Init result object
            ScriptingResult.Builder result = new ScriptingResult.Builder();

            //Copy input values to output values before doing something. Even if the process is
            //stopped for displaying debugger, we want to pass this values to the debugger.
            Map<String, String> inputValues = arguments.getValues();
            HashMap<String, String> outputValues = new HashMap<>();

            Set<String> keys = inputValues.keySet();
            for (String key : keys) {
                outputValues.put(key, inputValues.get(key));
            }

            result.values(outputValues);

            if (mScript.startsWith("debugger;")) {
                result.debugEnabled(true);
                return result.build();
            }

            cx.setLanguageVersion(Context.VERSION_1_8);

            //we need the following line because of
            // https://stackoverflow.com/questions/14454686/android-rhino-error-cant-load-this-type-of-class-file
            cx.setOptimizationLevel(-1);

            // Initialize the standard objects (Object, Function, etc.)
            // This must be done before scripts can be executed.
            Scriptable scope = cx.initStandardObjects();

            //Inject instances of java objects to our script in order to let them be used by it.
            //Inject our ScriptingResult.Builder. In the script is going to have the name result.
            //The we can use it like we were going to do in Java, but we are going to wrap it with
            //a JS function in order to use it. By doing that we hide the implementation from the script
            WrapFactory wrapFactory = cx.getWrapFactory();
            Object wrappedResult = wrapFactory.wrap(cx, scope, result, ScriptingResult.Builder.class);
            scope.put("result", scope, wrappedResult);

            //Inject our IQueryExecutor. In the script is going to have the name executor.
            //The we can use it like we were going to do in Java, but we are going to wrap it with
            //a JS function in order to use it. By doing that we hide the implementation from the script
            Object wrappedExecutor =
                    wrapFactory.wrap(cx, scope, mQueryExecutor, mQueryExecutor.getClass());
            scope.put("executor", scope, wrappedExecutor);

            // Now we can evaluate a script. Let's create a new object
            // using the object literal notation.
            cx.evaluateString(scope, mScript,
                    mScriptName + ": " + mFunction, 1, null);

            //pass output values in order to be modified by script
            //fixme maybe we should pass trigger too. Many times is useful to know what triggered the script
            Object functionArgs[] = { outputValues };
            Function function = (Function)scope.get(mFunction, scope);

            Boolean apply = (Boolean) function.call(cx, scope, scope, functionArgs);

            if (apply == null) {
                throw new RuntimeException(mFunction + " must return a boolean value.");
            }

            result.applyValues(apply);

            return result.build();
        } catch (EvaluatorException e) {
            throw new RuntimeException(e.details() + ": " + e.lineSource());
        }
        finally {
            Context.exit();
        }
    }

    private String addStandardMethods(String script) {
        return  script
                + "function query(select) {\n"
                + "return executor.query(select);"
                + "}"
                + ""
                + "function showAlert(msg) {"
                + "result.alert(msg);"
                + "}"
                + ""
                + "function showDialog(msg) {"
                + "result.dialog(msg);"
                + "}";
    }

}
