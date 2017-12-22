package ch.ibw.nds.appl2017.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Const {

    private Const() {
        throw new IllegalStateException("Class for constants only");
    }

    public static final String REST_API_DATEFORMAT_STRING = "yyyyMMdd";
    //NOSONAR
    public static final DateFormat REST_API_DATEFORMAT = new SimpleDateFormat(REST_API_DATEFORMAT_STRING);
}
