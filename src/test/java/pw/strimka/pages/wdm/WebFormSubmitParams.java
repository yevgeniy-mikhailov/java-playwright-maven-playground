package pw.strimka.pages.wdm;

public enum WebFormSubmitParams {
    MY_TEXT("my-text"), MY_PASSWORD("my-password"), MY_TEXTAREA("my-textarea"), MY_READONLY("my-readonly"), MY_SELECT("my-select"), MY_DATALIST("my-datalist"), MY_FILE("my-file"), MY_CHECK("my-check"), MY_RADIO("my-radio"), MY_COLORS("my-colors"), MY_DATE("my-date"), MY_RANGE("my-range"), MY_HIDDEN("my-hidden");

    public String getParameterName() {
        return parameterName;
    }

    private final String parameterName;

    WebFormSubmitParams(String parameterName) {
        this.parameterName = parameterName;
    }
}
