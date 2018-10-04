package quadible.com.js;

public class Model {

    private final String mActive;

    private final String mLoco;

    private Model(Builder builder) {
        mActive = builder.mActive;
        mLoco = builder.mLoco;
    }

    public String getActive() {
        return mActive;
    }

    public String getLoco() {
        return mLoco;
    }

    public static final class Builder {

        private String mActive = "active";

        private String mLoco = "loco";

        public Builder setActive(String active) {
            mActive = active;
            return this;
        }

        public Builder setLoco(String loco) {
            mLoco = loco;
            return this;
        }

        public Model build() {
            return new Model(this);
        }
    }
}
