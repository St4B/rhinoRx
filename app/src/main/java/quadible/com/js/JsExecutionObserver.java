package quadible.com.js;

import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;

public abstract class JsExecutionObserver extends DisposableObserver<ScriptingResult> {

    @Override
    public void onNext(ScriptingResult scriptingResult) {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof DebugNeededException) {
            DebugNeededException debugNeeded = (DebugNeededException) e;

            JsDebugger.getInstance()
                    .open(debugNeeded.getScriptName(), debugNeeded.getScript(),
                            debugNeeded.getMethod(), debugNeeded.getArguments());
        } else {
            Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onComplete() {

    }

}