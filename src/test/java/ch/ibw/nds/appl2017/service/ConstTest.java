package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import java.text.DateFormat;
import java.util.*;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static java.util.Calendar.DECEMBER;


@RunWith(OleasterRunner.class)
public class ConstTest {{

    describe("Test Class Behaviour", () -> {
        it("should be an instance of String", () -> {
            expect(Const.REST_API_DATEFORMAT_STRING).toBeInstanceOf(String.class);
            expect(Const.ALPHA_DATEFORMAT_STRING).toBeInstanceOf(String.class);
        });
        it("should be an instance of DateFormat", () -> {
            expect(Const.REST_API_DATEFORMAT).toBeInstanceOf(DateFormat.class);
            expect(Const.ALPHA_DATEFORMAT).toBeInstanceOf(DateFormat.class);
        });
    });

}}