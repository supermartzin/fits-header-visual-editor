package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.input.converters.ValueTypeConverter;
import cz.muni.fi.fits.models.inputData.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * TOTO description
 */
public class CmdArgsProcessingHelperTests {

    private static final Path FILE_PATH = Paths.get("test-files.in");
    private static final Path DIR_PATH = Paths.get("test-files_dir.in");

    private TypeConverter _converter;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Files.createFile(FILE_PATH);
        _converter = new ValueTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(FILE_PATH);
        _converter = null;
    }

    @Test
    public void testExtractFilesData_NullFilePath() throws Exception {
        exception.expect(IllegalArgumentException.class);
        CmdArgumentsProcessorHelper.extractFilesData(null);
    }

    @Test
    public void testExtractFilesData_FilePathDoesNotExists() throws Exception {
        Path path = Paths.get("files_not_exists.in");

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("does not exist");
        CmdArgumentsProcessorHelper.extractFilesData(path.toString());
    }

    @Test
    public void testExtractFilesData_FilePathIsDirectory() throws Exception {
        Files.createDirectory(DIR_PATH);
        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is not a file");
        try {
            CmdArgumentsProcessorHelper.extractFilesData(DIR_PATH.toString());
        }
        finally {
            Files.deleteIfExists(DIR_PATH);
        }
    }

    @Test
    public void testExtractFilesData_FileIsEmpty() throws Exception {
        Collection<File> fitsFiles = CmdArgumentsProcessorHelper.extractFilesData(FILE_PATH.toString());

        assertTrue(fitsFiles.isEmpty());
    }

    @Test
    public void testExtractFilesData_CorrectFile() throws Exception {
        List<String> fitsFilesLines = Arrays.asList("sample1.fits", "sample2.fits", "sample3.fits");
        Files.write(FILE_PATH, fitsFilesLines);

        HashSet<File> fitsFiles = new HashSet<>(CmdArgumentsProcessorHelper.extractFilesData(FILE_PATH.toString()));

        assertTrue(fitsFiles.size() == 3);
    }

    @Test
    public void testExtractAddNewRecordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "add", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
    }

    @Test
    public void testExtractAddNewRecordData_CorrectParameters() throws Exception {
        String[] args = new String[] { "add", "-u", FILE_PATH.toString(), "KEYWORD", "VALUE", "COMMENT" };

        AddNewRecordInputData anrid = CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);

        assertEquals("KEYWORD".toUpperCase(), anrid.getKeyword());
        assertTrue(anrid.getValue() instanceof String);
        assertEquals("VALUE", anrid.getValue());
        assertEquals("COMMENT", anrid.getComment());
        assertTrue(anrid.updateIfExists());
    }

    @Test
    public void testExtractAddNewRecordData_WrongSwitchParameter() throws Exception {
        String[] args = new String[] { "add", "-update", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractAddNewRecordData(args, _converter);
    }

    @Test
    public void testExtractAddNewToIndexData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "add_ix", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractAddNewToIndexData(args, _converter);
    }

    @Test
    public void testExtractAddNewToIndexData_InvalidIndex() throws Exception {
        String[] args = new String[] { "add_ix", FILE_PATH.toString(), "one","KEYWORD", "VALUE" };

        exception.expect(IllegalInputDataException.class);
        CmdArgumentsProcessorHelper.extractAddNewToIndexData(args, _converter);
    }

    @Test
    public void testExtractAddNewToIndexData_WrongSwitchParameter() throws Exception {
        String[] args = new String[] { "add_ix", "-remove", FILE_PATH.toString(), "KEYWORD", "VALUE" };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractAddNewToIndexData(args, _converter);
    }

    @Test
    public void testExtractAddNewToIndexData_CorrectParameters() throws Exception {
        String[] args = new String[] { "add_ix", "-rm", FILE_PATH.toString(), "25", "KEYWORD", "VALUE", "COMMENT" };

        AddNewToIndexInputData antiid = CmdArgumentsProcessorHelper.extractAddNewToIndexData(args, _converter);

        assertEquals(25, antiid.getIndex());
        assertEquals("KEYWORD".toUpperCase(), antiid.getKeyword());
        assertEquals("VALUE", antiid.getValue());
        assertEquals("COMMENT", antiid.getComment());
        assertTrue(antiid.removeOldIfExists());
    }

    @Test
    public void testExtractRemoveByKeywordData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "remove", FILE_PATH.toString(), "KEYWORD", "ARG1", "ARG2" };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractRemoveByKeywordData(args);
    }

    @Test
    public void testExtractRemoveByKeywordData_CorrectParameters() throws Exception {
        String[] args = new String[] { "remove", FILE_PATH.toString(), "KEYWORD" };

        RemoveByKeywordInputData rbkid = CmdArgumentsProcessorHelper.extractRemoveByKeywordData(args);

        assertEquals("KEYWORD".toUpperCase(), rbkid.getKeyword());
    }

    @Test
    public void testExtractRemoveByIndexData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "remove_ix", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractRemoveByIndexData(args);
    }

    @Test
    public void testExtractRemoveByIndexData_InvalidIndex() throws Exception {
        String[] args = new String[] { "remove_ix", FILE_PATH.toString(), "index" };

        exception.expect(IllegalInputDataException.class);
        CmdArgumentsProcessorHelper.extractRemoveByIndexData(args);
    }

    @Test
    public void testExtractRemoveByIndexData_CorrectParameters() throws Exception {
        String[] args = new String[] { "remove_ix", FILE_PATH.toString(), "69" };

        RemoveByIndexInputData rbiid = CmdArgumentsProcessorHelper.extractRemoveByIndexData(args);

        assertEquals(69, rbiid.getIndex());
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
        String[] args = new String[] { "change_kw", "-rm", FILE_PATH.toString(), "OLD_KEYWORD", "NEW_KEYWORD" };

        ChangeKeywordInputData ckid = CmdArgumentsProcessorHelper.extractChangeKeywordData(args);
        assertEquals("OLD_KEYWORD".toUpperCase(), ckid.getOldKeyword());
        assertEquals("NEW_KEYWORD".toUpperCase(), ckid.getNewKeyword());
        assertTrue(ckid.removeValueOfNewIfExists());
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
    public void testExtractChangeValueByKeywordData_CorrectParameters() throws Exception {
        String[] args = new String[] { "change", FILE_PATH.toString(), "KEYWORD", "VALUE", "COMMENT" };

        ChangeValueByKeywordInputData cvbkid = CmdArgumentsProcessorHelper.extractChangeValueByKeywordData(args, _converter);
        assertEquals("KEYWORD".toUpperCase(), cvbkid.getKeyword());
        assertFalse(cvbkid.addNewIfNotExists());
        assertEquals("VALUE", cvbkid.getValue());
        assertEquals("COMMENT", cvbkid.getComment());
    }

    @Test
    public void testExtractChainRecordsData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "chain", "-u", FILE_PATH.toString() };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_WrongFirstSwitchParameter() throws Exception {
        String[] args = new String[] { "chain", "-upd", "-s", FILE_PATH.toString() };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_WrongSecondSwitchParameter() throws Exception {
        String[] args = new String[] { "chain", "-s", "-upd", FILE_PATH.toString() };

        exception.expect(InvalidSwitchParameterException.class);
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_EmptyChainParameters() throws Exception {
        String[] args = new String[] { "chain", "-s", FILE_PATH.toString(), "KEYWORD", "COMMENT" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("not contain any keyword or constant");
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_CorrectParameters() throws Exception {
        String[] args = new String[] { "chain", "-s", FILE_PATH.toString(), "KEYWORD", "-c=CONSTANT 1", "-k=KEYWORD 1", "-k=KEYWORD 2", "-c=CONSTANT 2", "COMMENT" };

        ChainRecordsInputData crid = CmdArgumentsProcessorHelper.extractChainRecordsData(args);

        assertTrue(crid.skipIfChainKwNotExists());
        assertFalse(crid.updateIfExists());
        assertEquals("KEYWORD", crid.getKeyword());
        assertEquals("COMMENT", crid.getComment());
        assertEquals(4, crid.getChainValues().size());
    }
}
