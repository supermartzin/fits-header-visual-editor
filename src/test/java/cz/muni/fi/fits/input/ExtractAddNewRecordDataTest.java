package cz.muni.fi.fits.input;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * TODO description
 */
public class ExtractAddNewRecordDataTest {

    private static final Path FILE_PATH = Paths.get("files.in");

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



    // TODO to validator test
    /*@Test
    public void testExtractAddNewRecordData_TooLongKeyword() throws Exception {
        String[] args = new String[] { "add_by_kw", FILE_PATH.toString(), "TOO_LONG_KEYWORD", "value" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("exceeds allowed length");
        CmdArgsProcessingHelper.extractAddNewRecordData(args);
    }

    @Test
    public void testExtractAddNewRecordData_TooLongValue() throws Exception {
        String[] args = new String[] { "add_by_kw", FILE_PATH.toString(), "KEYWORD", "value that is too long to insert to FITS File header as a header keyword value - maximum length of value is 70 characters" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("exceeds allowed length");
        CmdArgsProcessingHelper.extractAddNewRecordData(args);
    }

    @Test
    public void testExtractAddNewRecordData_TooLongComment() throws Exception {
        String[] args = new String[] { "add_by_kw", FILE_PATH.toString(), "KEYWORD", "value that is not too long to insert to FITS File header as a header keyword value", "but with the commect the header record is too long" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("exceeds allowed length");
        CmdArgsProcessingHelper.extractAddNewRecordData(args);
    }*/
}