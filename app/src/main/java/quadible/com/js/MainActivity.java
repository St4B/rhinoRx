package quadible.com.js;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        Button trigger = findViewById(R.id.btnTrigger);
        trigger.setOnClickListener(view -> Observable.just(new Model.Builder().build())
                .subscribeOn(Schedulers.newThread())
                .map(mMapper::transform)
                .flatMapSingle(mEngine::execute)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver()));
    }

    public class MyObserver extends DisposableObserver<ScriptingResult> {


        @Override
        public void onNext(ScriptingResult result) {
            Model model = mMapper.transform(result);
            TextView text = findViewById(R.id.txt);
            text.setText(model.toString());
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onComplete() {}
    }
}
