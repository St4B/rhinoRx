package quadible.com.js;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

public class App extends Application {

    private static Context sContext;

    private ActivityTracker mCallback = new ActivityTracker();

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        registerActivityLifecycleCallbacks(mCallback);
    }

    public static Context getContext() {
        return sContext;
    }

    public static Activity getCurrentActivity() {
        return ((App) sContext).mCallback.mActivity;
    }

    private static class ActivityTracker implements ActivityLifecycleCallbacks {

        Activity mActivity;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            mActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            mActivity = null;
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
