package ch.ibw.nds.appl2017.service;

public class ErrorMessage {

    String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public static ErrorMessage createErrorMessage (String message) {
        return new ErrorMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
