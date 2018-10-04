package quadible.com.js;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Arguments {

    private final String mTrigger;

    private final Map<String, String> mValues;

    private Arguments(Builder builder) {
        mTrigger = builder.mTrigger;
        mValues = Collections.unmodifiableMap(builder.mValues);
    }

    public String getTrigger() {
        return mTrigger;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    public  static final class Builder {

        private String mTrigger = "not defined";

        private HashMap<String, String> mValues = new HashMap<>();

        public Builder putValue(String name, String value) {
            mValues.put(name, value);
            return this;
        }

        public Builder trigger(String trigger) {
            mTrigger = trigger;
            return this;
        }

        public Arguments build() {
            return new Arguments(this);
        }
    }
}
