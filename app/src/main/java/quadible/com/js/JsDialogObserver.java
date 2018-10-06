package quadible.com.js;

import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import io.reactivex.observers.DisposableObserver;

public class JsDialogObserver extends DisposableObserver<DialogDefinition> {

    @Override
    public void onNext(DialogDefinition definition) {
        new AlertDialog.Builder(App.getCurrentActivity())
                .setTitle(definition.getTitle())
                .setMessage(definition.getMessage())
                .setPositiveButton(definition.getPositiveButtonTitle(), null)
                .setNegativeButton(definition.getNegativeButtonTitle(), null)
                .show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete() {
        dispose();
    }
}