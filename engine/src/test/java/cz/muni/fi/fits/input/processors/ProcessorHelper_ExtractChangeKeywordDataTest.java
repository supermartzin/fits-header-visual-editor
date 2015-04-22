package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.inputData.ChangeKeywordInputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for extraction of input data for operation <b>Change keyword of record</b>
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ProcessorHelper_ExtractChangeKeywordDataTest {

    private static final Path FILE_PATH = Paths.get("test-files.in");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Files.createFile(FILE_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testExtractChangeKeywordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "change_kw", FILE_PATH.toString(), "KEYWORD" };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractChangeKeywordData(args);
    }

    @Test
    public void testExtractChangeKeywordData_WrongSwtichParameter() throws Exception {
        String[] args = new String[] { "change_kw", "-remove", FILE_PATH.toString(), "OLD_KEYWORD", "NEW_KEYWORD" };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractChangeKeywordData(args);
    }

    @Test
    public void testExtractChangeKeywordData_CorrectParameters() throws Exception {
        String[] args = new String[] { "change_kw", "-rm", FILE_PATH.toString(), "OLD_KEYWORD", "5963" };

        ChangeKeywordInputData ckid = CmdArgumentsProcessorHelper.extractChangeKeywordData(args);
        assertEquals("OLD_KEYWORD".toUpperCase(), ckid.getOldKeyword());
        assertEquals("5963".toUpperCase(), ckid.getNewKeyword());
        assertTrue(ckid.removeValueOfNewIfExists());
    }
}
