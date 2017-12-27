package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.service.Const;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static org.junit.Assert.*;

/**
 * Created by dieterbiedermann on 27.12.17.
 */
@RunWith(OleasterRunner.class)
public class DateUtilsTest {{

    describe("asDate", () -> {
        it("should return Date object", () -> {
            LocalDate localDate = LocalDate.parse("2017-01-01");

            Date result = DateUtils.asDate(localDate);

            expect(result).toBeInstanceOf(Date.class);
        });
    });

    describe("asLocalDate", () -> {
        it("should return LocalDate object", () -> {
            Date date = Const.ALPHA_DATEFORMAT.parse("2017-01-01");

            LocalDate result = DateUtils.asLocalDate(date);

            expect(result).toBeInstanceOf(LocalDate.class);
        });
    });

}}