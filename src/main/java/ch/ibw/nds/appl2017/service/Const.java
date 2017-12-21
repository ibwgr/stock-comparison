package ch.ibw.nds.appl2017.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Const {
    public static final String REST_API_DATEFORMAT_STRING = "yyyyMMdd";
    public static DateFormat REST_API_DATEFORMAT = new SimpleDateFormat(REST_API_DATEFORMAT_STRING);
}
