package ch.ibw.nds.appl2017.service;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;


@RunWith(OleasterRunner.class)
public class ErrorMessageTest {{

    describe("getMessage", () -> {
        it("should return the error message", () -> {
            ErrorMessage errorMessage = ErrorMessage.createErrorMessage("test");
            expect(errorMessage.getMessage()).toEqual("test");
            errorMessage.setMessage("new");
            expect(errorMessage.getMessage()).toEqual("new");
        });
    });

}}