package quadible.com.js;

public final class DialogDefinition {

    private final String mTitle;

    private final String mMessage;

    private final String mNegativeButtonTitle;

    private final String mPositiveButtonTitle;

    private DialogDefinition(Builder builder) {
        mTitle = builder.mTitle;
        mMessage = builder.mMessage;
        mNegativeButtonTitle = builder.mNegativeButtonTitle;
        mPositiveButtonTitle = builder.mPositiveButtonTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getNegativeButtonTitle() {
        return mNegativeButtonTitle;
    }

    public String getPositiveButtonTitle() {
        return mPositiveButtonTitle;
    }

    public static final class Builder {

        private String mTitle;

        private String mMessage;

        private String mNegativeButtonTitle;

        private String mPositiveButtonTitle;

        public Builder title(String title) {
            mTitle = title;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Builder negativeButtonTitle(String negativeButtonTitle) {
            mNegativeButtonTitle = negativeButtonTitle;
            return this;
        }

        public Builder positiveButtonTitle(String positiveButtonTitle) {
            mPositiveButtonTitle = positiveButtonTitle;
            return this;
        }

        public DialogDefinition build() {
            return new DialogDefinition(this);
        }
    }
}
