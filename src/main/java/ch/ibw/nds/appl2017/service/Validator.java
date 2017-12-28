package ch.ibw.nds.appl2017.service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class Validator {

    private Validator() {
    }

    public static void validateInput(List<String> stockSymbols, String fromDateString, String toDateString) {
        if (fromDateString == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!fromDateString.matches(Const.REST_API_DATEFORMAT_REGEX_PATTERN)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (toDateString == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!toDateString.matches(Const.REST_API_DATEFORMAT_REGEX_PATTERN)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (stockSymbols == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        for (String stockSymbol : stockSymbols) {
            if (!stockSymbol.matches("^[A-Z]{1,5}$")) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }
}
