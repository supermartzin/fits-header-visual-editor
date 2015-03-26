package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * TODO description
 */
public class CmdArgsProcessingHelperTest {

    private static final Path FILE_PATH = Paths.get("files.in");
    private static final Path DIR_PATH = Paths.get("files_dir.in");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Files.createFile(FILE_PATH);
        Files.createDirectory(DIR_PATH);
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
        Files.deleteIfExists(DIR_PATH);
    }

    @Test
    public void testExtractFilesData_NullFilePath() throws Exception {
        exception.expect(IllegalArgumentException.class);
        CmdArgsProcessingHelper.extractFilesData(null);
    }

    @Test
    public void testExtractFilesData_FilePathDoesNotExists() throws Exception {
        Path path = Paths.get("files_not_exists.in");

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("does not exist");
        CmdArgsProcessingHelper.extractFilesData(path.toString());
    }

    @Test
    public void testExtractFilesData_FilePathIsDirectory() throws Exception {
        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is invalid");
        CmdArgsProcessingHelper.extractFilesData(DIR_PATH.toString());
    }

    @Test
    public void testExtractFilesData_FileIsEmpty() throws Exception {
        Collection<File> fitsFiles = CmdArgsProcessingHelper.extractFilesData(FILE_PATH.toString());

        assertTrue(fitsFiles.isEmpty());
    }

    @Test
    public void testExtractFilesData_CorrectFile() throws Exception {
        List<String> fitsFilesLines = Arrays.asList("sample1.fits", "sample2.fits", "sample3.fits");
        Files.write(FILE_PATH, fitsFilesLines);

        Collection<File> fitsFiles = CmdArgsProcessingHelper.extractFilesData(FILE_PATH.toString());

        assertTrue(fitsFiles.size() == 3);
    }

    @Test
    public void testExtractAddNewRecordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "add_by_kw", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgsProcessingHelper.extractAddNewRecordData(args);
    }

    @Test
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
    }

    @Test
    public void testExtractAddNewToIndexData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "add_to_ix", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgsProcessingHelper.extractAddNewToIndexData(args);
    }

    @Test
    public void testExtractAddNewToIndexData_InvalidIndex() throws Exception {
        String[] args = new String[] { "add_to_ix", FILE_PATH.toString(), "-1","KEYWORD", "VALUE" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("invalid index");
        CmdArgsProcessingHelper.extractAddNewToIndexData(args);
    }
}