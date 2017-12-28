package ch.ibw.nds.appl2017.service;

public class ErrorMessage {

    String errorMessage;

    private ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static ErrorMessage createErrorMessage (String errorMessage) {
        return new ErrorMessage(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
