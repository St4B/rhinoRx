package quadible.com.js;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final ModelToArgumentsMapper mMapper = new ModelToArgumentsMapper();

    private final String mScriptName = "MyScript";

    private final String mScript =
            "function HeaderPropertyChanged(header) {"
            + "header.put(\"loco\", \"modo\");"
            + "header.put(\"active\", \"member\");"
            + "showAlert(\"koukou!\");"
            + "var data = query(\"select * from somewhere\");"
            + "header.put(data[0][0], data[0][1]); "
            + "header.put(data[1][0], data[1][1]); "
            + "return true;"
            + "}";

    private final String mFunctionName = "HeaderPropertyChanged";

    private final IQueryExecutor mQueryExecutor = new DummyRepository();

    private final JavascriptEngine mEngine =
            new JavascriptEngine(mScriptName, mScript, mFunctionName, mQueryExecutor);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just(new Model.Builder().build())
                .subscribeOn(Schedulers.newThread())
                .map(mMapper::transform)
                .flatMapSingle(mEngine::execute)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver());
    }

    public class MyObserver extends DisposableObserver<ScriptingResult> {

        private String TAG = "St4B";

        @Override
        public void onNext(ScriptingResult result) {
            Log.d(TAG, "onNext() called with: result = [" + result + "]");
            Model model = mMapper.transform(result); //Do something with it bro! :D
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "onError() called with: e = [" + e + "]");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete() called");
        }
    }
}
