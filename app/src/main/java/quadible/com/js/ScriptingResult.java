package quadible.com.js;

import java.util.ArrayList;
import java.util.HashMap;

public final class ScriptingResult {

    private final HashMap<String, String> mValues;

    private final ArrayList<String> mAlerts;

    private final ArrayList<String> mDialogs;

    private final boolean applyValues;

    private final boolean isDebugEnabled;

    public HashMap<String, String> getValues() {
        return mValues;
    }

    public ArrayList<String> getAlerts() {
        return mAlerts;
    }

    public ArrayList<String> getDialogs() {
        return mDialogs;
    }

    public boolean isApplyValues() {
        return applyValues;
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    private ScriptingResult(Builder builder) {
        mValues =  builder.mValues;
        mAlerts = builder.mAlerts;
        mDialogs = builder.mDialogs;
        applyValues = builder.applyValues;
        isDebugEnabled = builder.isDebugEnabled;
    }

    public static final class Builder {

        private HashMap<String, String> mValues = new HashMap<>();

        private ArrayList<String> mAlerts = new ArrayList<>();

        private ArrayList<String> mDialogs = new ArrayList<>();

        private boolean applyValues = false;

        private boolean isDebugEnabled = false;

        public Builder values(HashMap<String, String> values) {
            mValues = values;
            return this;
        }

        public Builder alert(String msg) {
            mAlerts.add(msg);
            return this;
        }

        public Builder dialog(String msg) {
            mDialogs.add(msg);
            return this;
        }

        public Builder applyValues(boolean apply) {
            applyValues = apply;
            return this;
        }

        public Builder debugEnabled(boolean debugEnabled) {
            isDebugEnabled = debugEnabled;
            return this;
        }

        public ScriptingResult build() {
            return new ScriptingResult(this);
        }
    }

}
