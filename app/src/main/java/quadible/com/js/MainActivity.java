package quadible.com.js;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final ModelToArgumentsMapper mMapper = new ModelToArgumentsMapper();

    private final IQueryExecutor mQueryExecutor = new DummyRepository();

    private final String mScriptName = "MyScript";

    private final ScriptLoader mScriptLoader = new ScriptLoader();

    private final JsDialogPublisher mJsDialogPublisher = new JsDialogPublisher();

    private final JavascriptEngine mEngine =
            new JavascriptEngine(
                    mScriptName, mScriptLoader.load(mScriptName), ScriptLoader.sFunctionName, mQueryExecutor, mJsDialogPublisher);

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<DialogDefinition> dialog = mJsDialogPublisher.observe()
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = dialog.subscribeWith(new JsDialogObserver());
        mDisposable.add(disposable);

        Button trigger = findViewById(R.id.btnTrigger);
        trigger.setOnClickListener(view -> Observable.just(new Model.Builder().build())
                .subscribeOn(Schedulers.newThread())
                .map(mMapper::transform)
                .flatMapSingle(mEngine::execute)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    public class MyObserver extends JsExecutionObserver {

        @Override
        public void onNext(ScriptingResult result) {
            Model model = mMapper.transform(result);
            TextView text = findViewById(R.id.txt);
            text.setText(model.toString());
        }

    }

}
