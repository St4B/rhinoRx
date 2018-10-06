package quadible.com.js;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Arguments implements Parcelable {

    private final String mTrigger;

    private final Map<String, String> mValues;

    private Arguments(Builder builder) {
        mTrigger = builder.mTrigger;
        mValues = Collections.unmodifiableMap(builder.mValues);
    }

    protected Arguments(Parcel in) {
        mTrigger = in.readString();
        mValues = Collections.unmodifiableMap(new HashMap<>()); //fixme
    }

    public static final Creator<Arguments> CREATOR = new Creator<Arguments>() {

        @Override
        public Arguments createFromParcel(Parcel in) {
            return new Arguments(in);
        }

        @Override
        public Arguments[] newArray(int size) {
            return new Arguments[size];
        }
    };

    public String getTrigger() {
        return mTrigger;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTrigger);
        //fixme mValues dest.writePersistableBundle();
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
