package quadible.com.js;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DebugDialogFragment extends DialogFragment {

    private static final String BUNDLE_KEY_SCRIPT = "script";

    private static final String BUNDLE_KEY_METHOD = "method";

    public static DebugDialogFragment newInstance(String script, String method) {
        DebugDialogFragment fragment = new DebugDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_SCRIPT, script);
        args.putString(BUNDLE_KEY_METHOD, method);

        fragment.setArguments(args);
        return fragment;
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

        EditText editText = dialogView.findViewById(R.id.etScript);

        editText.setText(getArguments().getString(BUNDLE_KEY_SCRIPT));
        return builder.create();
    }

}
