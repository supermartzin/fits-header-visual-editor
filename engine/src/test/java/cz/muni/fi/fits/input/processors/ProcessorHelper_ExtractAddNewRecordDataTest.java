package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.DefaultTypeConverter;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.models.inputData.AddNewRecordInputData;
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
 * Tests for extraction of input data for operation <b>Add new record</b>
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.1
 */
public class ProcessorHelper_ExtractAddNewRecordDataTest {

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
    public void testExtractAddNewRecordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "add", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
    }

    @Test
    public void testExtractAddNewRecordData_NoSwitchParameter() throws Exception {
        String[] args = new String[] { "add", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
        assertNotNull(anrid);
        assertFalse(anrid.updateIfExists());
    }

    @Test
    public void testExtractAddNewRecordData_WrongSwitchParameter() throws Exception {
        String[] args = new String[] { "add", "-update", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
    }

    @Test
    public void testExtractAddNewRecordData_ValidSwitchParameter() throws Exception {
        String[] args = new String[] { "add", "-u", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
        assertNotNull(anrid);
        assertTrue(anrid.updateIfExists());
    }

    @Test
    public void testExtractAddNewRecordData_ContainsComment() throws Exception {
        String[] args = new String[] { "add", "-u", FILE_PATH.toString(), "KEYWORD", "VALUE", "comment" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);

        assertNotNull(anrid);
        assertNotNull(anrid.getComment());
        assertEquals("comment", anrid.getComment());
    }

    @Test
    public void testExtractAddNewRecordData_DoesNotContainComment() throws Exception {
        String[] args = new String[] { "add", "-u", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);

        assertNotNull(anrid);
        assertNull(anrid.getComment());
    }

    @Test
    public void testExtractAddNewRecordData_Keyword() throws Exception {
        String[] args = new String[] { "add", FILE_PATH.toString(), "keyWORD", "true" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);

        assertNotNull(anrid);
        assertEquals("KEYWORD", anrid.getKeyword());
    }

    @Test
    public void testExtractAddNewRecordData_CorrectParameters() throws Exception {
        String[] args = new String[] { "add", FILE_PATH.toString(), "KEYWORD", "true" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);

        assertNotNull(anrid);
        assertEquals("KEYWORD", anrid.getKeyword());
        assertTrue((Boolean) anrid.getValue());
        assertNull(anrid.getComment());
        assertFalse(anrid.updateIfExists());
    }


}
