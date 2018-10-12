package quadible.com.js;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DebugDialogFragment extends DialogFragment {

    public static final String TAG = DebugDialogFragment.class.getSimpleName();

    private static final String BUNDLE_KEY_SCRIPT_NAME = "scriptName";

    private static final String BUNDLE_KEY_SCRIPT = "script";

    private static final String BUNDLE_KEY_METHOD = "method";

    private static final String BUNDLE_KEY_ARGUMENTS = "arguments";

    private final JsDialogPublisher mJsDialogPublisher = new JsDialogPublisher();

    private final IQueryExecutor mQueryExecutor = new DummyRepository();

    private Arguments mArguments;

    private EditText mScriptEditText;

    private String mScriptName;

    private String mMethodName;

    public static DebugDialogFragment newInstance(
            String scriptName, String script, String method, Arguments arguments) {
        DebugDialogFragment fragment = new DebugDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_SCRIPT_NAME, scriptName);
        args.putString(BUNDLE_KEY_SCRIPT, script);
        args.putString(BUNDLE_KEY_METHOD, method);
        args.putParcelable(BUNDLE_KEY_ARGUMENTS, arguments);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScriptName = getArguments().getString(BUNDLE_KEY_SCRIPT_NAME);
        mMethodName = getArguments().getString(BUNDLE_KEY_METHOD);
        mArguments = getArguments().getParcelable(BUNDLE_KEY_ARGUMENTS);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setNeutralButton("Discard & exit", null);
        builder.setPositiveButton("Execute", null);
        builder.setNegativeButton("Save", null);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_debug_script, null);
        builder.setView(dialogView);

        mScriptEditText = dialogView.findViewById(R.id.etScript);

        if (savedInstanceState == null) {
            mScriptEditText.setText(getArguments().getString(BUNDLE_KEY_SCRIPT));
        }
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(mExecute);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(mSaveAndExecute);
    }

    private final View.OnClickListener mExecute = v -> {
        String script = mScriptEditText.getText().toString();
        execute(script);
    };

    private final View.OnClickListener mSaveAndExecute = v -> {
        String script = mScriptEditText.getText().toString();
        ScriptLoader scriptLoader = new ScriptLoader();
        scriptLoader.save(mScriptName, script);
        execute(script);
    };

    private void execute(String script) {
        Observable.just(mArguments)
                .subscribeOn(Schedulers.newThread())
                .flatMapSingle(arguments ->
                        new JavascriptEngine(mScriptName, script, mMethodName, mQueryExecutor, mJsDialogPublisher)
                                .debug(arguments))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DebugObserver());
    }

    public class DebugObserver extends JsExecutionObserver {

        @Override
        public void onNext(ScriptingResult result) {
            ModelToArgumentsMapper mMapper = new ModelToArgumentsMapper();
            Model model = mMapper.transform(result);
            Toast.makeText(getActivity(), model.toString(), Toast.LENGTH_LONG).show();
        }

    }

}
