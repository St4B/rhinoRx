package quadible.com.js;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class JsDialogPublisher {

    private final PublishSubject<String> mSubject = PublishSubject.create();

    public void publish(String msg) {
        mSubject.onNext(msg);
    }

    public Observable<String> observe() {
        return mSubject;
    }

}
