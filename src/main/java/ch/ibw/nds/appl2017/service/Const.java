package ch.ibw.nds.appl2017.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Const {

    private Const() {
        throw new IllegalStateException("Class for constants only");
    }


    public static final String REST_API_DATEFORMAT_STRING = "yyyyMMdd";
    public static final String REST_API_DATEFORMAT_REGEX_PATTERN = "\\d{4}\\d{2}\\d{2}";
    public static final DateFormat REST_API_DATEFORMAT = new SimpleDateFormat(REST_API_DATEFORMAT_STRING);


    public static final String ALPHA_DATEFORMAT_STRING = "yyyy-MM-dd";
    public static final DateFormat ALPHA_DATEFORMAT = new SimpleDateFormat(ALPHA_DATEFORMAT_STRING);
}
