package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.DefaultTypeConverter;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.models.inputData.ChangeValueByKeywordInputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Tests for extraction of input data for operation <b>Change value of record</b>
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ProcessorHelper_ExtractChangeValueByKeywordDataTest {

    private static final Path FILE_PATH = Paths.get("test-files.in");

    private TypeConverter _converter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Files.createFile(FILE_PATH);
        _converter = new DefaultTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
        _converter = null;
    }

    @Test
    public void testExtractChangeValueByKeywordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "change", FILE_PATH.toString(), "KEYWORD", "VALUE", "COMMENT", "REDUNDANT_ARG" };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(args, _converter);
    }

    @Test
    public void testExtractChangeValueByKeywordData_WrongSwitchParameter() throws Exception {
        String[] args = new String[] { "change", "-add", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(args, _converter);
    }

    @Test
    public void testExtractChangeValueByKeywordData_CorrectParameters1() throws Exception {
        String[] args = new String[] { "change", FILE_PATH.toString(), "KEYWORD", "VALUE", "COMMENT" };

        ChangeValueByKeywordInputData cvbkid = CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(args, _converter);
        assertEquals("KEYWORD".toUpperCase(), cvbkid.getKeyword());
        assertFalse(cvbkid.addNewIfNotExists());
        assertEquals("VALUE", cvbkid.getValue());
        assertEquals("COMMENT", cvbkid.getComment());
    }

    @Test
    public void testExtractChangeValueByKeywordData_CorrectParameters2() throws Exception {
        String[] args = new String[] { "change", "-a", FILE_PATH.toString(), "KEYWORD", "456." };

        ChangeValueByKeywordInputData cvbkid = CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(args, _converter);
        assertEquals("KEYWORD".toUpperCase(), cvbkid.getKeyword());
        assertTrue(cvbkid.addNewIfNotExists());
        assertEquals(456.0, cvbkid.getValue());
        assertNull("COMMENT", cvbkid.getComment());
    }
}
