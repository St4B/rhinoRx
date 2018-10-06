package quadible.com.js;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ScriptingResult {

    private final Map<String, String> mValues;

    private final boolean applyValues;

    private ScriptingResult(Builder builder) {
        mValues =  Collections.unmodifiableMap(builder.mValues);
        applyValues = builder.applyValues;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    public boolean applyValues() {
        return applyValues;
    }

    public static final class Builder {

        private HashMap<String, String> mValues = new HashMap<>();

        private boolean applyValues = false;

        public Builder values(HashMap<String, String> values) {
            mValues = values;
            return this;
        }

        public Builder applyValues(boolean apply) {
            applyValues = apply;
            return this;
        }
        
        public ScriptingResult build() {
            return new ScriptingResult(this);
        }
    }

}
