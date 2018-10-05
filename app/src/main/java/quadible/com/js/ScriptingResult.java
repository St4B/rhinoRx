package quadible.com.js;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ScriptingResult {

    private final Map<String, String> mValues;

    private final boolean applyValues;

    private final boolean isDebugEnabled;

    private ScriptingResult(Builder builder) {
        mValues =  Collections.unmodifiableMap(builder.mValues);
        applyValues = builder.applyValues;
        isDebugEnabled = builder.isDebugEnabled;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    public boolean isApplyValues() {
        return applyValues;
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
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
