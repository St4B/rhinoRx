package quadible.com.js;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class JsDebugger {

    private static JsDebugger sInstance;

    private JsDebugger() { }

    public static JsDebugger getInstance() {
        if (sInstance == null) sInstance = new JsDebugger();
        return sInstance;
    }

    public void open(String scriptName, String script, String method, Arguments arguments) {
        Activity activity = App.getCurrentActivity();

        if (activity instanceof FragmentActivity) {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(DebugDialogFragment.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            DebugDialogFragment debugDialogFragment =
                    DebugDialogFragment.newInstance(scriptName, script, method, arguments);
            debugDialogFragment.show(ft, DebugDialogFragment.TAG);
        }
    }
}
