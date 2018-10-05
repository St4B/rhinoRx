package quadible.com.js;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class JsDialogPublisher {

    private final PublishSubject<DialogDefinition> mSubject = PublishSubject.create();

    public void publish(
            String title, String msg, String positiveButtonTitle, String negativeButtonTitle) {
        DialogDefinition definition = new DialogDefinition.Builder()
                .title(title)
                .message(msg)
                .positiveButtonTitle(positiveButtonTitle)
                .negativeButtonTitle(negativeButtonTitle)
                .build();

        mSubject.onNext(definition);
    }

    public Observable<DialogDefinition> observe() {
        return mSubject;
    }

    public void complete() {
        mSubject.onComplete();
    }

}
