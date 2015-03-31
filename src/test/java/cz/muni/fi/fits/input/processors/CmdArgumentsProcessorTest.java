package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.models.inputData.ChangeKeywordInputData;
import cz.muni.fi.fits.models.inputData.InputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * TODO description
 */
public class CmdArgumentsProcessorTest {

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

    @Test
    public void testGetProcessedInput_NullArguments() throws Exception {
        InputProcessor inputProcessor = new CmdArgumentsProcessor(null);

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("Arguments are null");
        inputProcessor.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_NoArguments() throws Exception {
        InputProcessor inputProcessor = new CmdArgumentsProcessor(new String[]{});

        exception.expect(WrongNumberOfParametersException.class);
        inputProcessor.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_WrongNumberOfArguments() throws Exception {
        String[] args = new String[] { "add" };
        InputProcessor inputProcessor = new CmdArgumentsProcessor(args);

        exception.expect(WrongNumberOfParametersException.class);
        inputProcessor.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_BadOperation() throws Exception {
        String[] args = new String[] { "add_bad", "files.in" };
        InputProcessor inputProcessor = new CmdArgumentsProcessor(args);

        exception.expect(UnknownOperationException.class);
        inputProcessor.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_ValidData() throws Exception {
        Files.write(FILE_PATH, Arrays.asList("sample1.fits", "sample2.fits", "sample3.fits"));
        String[] args = new String[] { "change_kw", "-rm", FILE_PATH.toString(), "OLD_KEYWORD", "NEW KEYWORD" };
        InputProcessor inputProcessor = new CmdArgumentsProcessor(args);

        InputData inputData = inputProcessor.getProcessedInput();

        assertTrue(inputData != null);
        assertTrue(inputData.getOperationType() == OperationType.CHANGE_KEYWORD);
        assertTrue(inputData instanceof ChangeKeywordInputData);
        assertEquals(3, inputData.getFitsFiles().size());

        ChangeKeywordInputData ckid = (ChangeKeywordInputData)inputData;
        assertEquals("OLD_KEYWORD".toUpperCase(), ckid.getOldKeyword());
        assertEquals("NEW KEYWORD".toUpperCase(), ckid.getNewKeyword());
        assertTrue(ckid.removeValueOfNewIfExists());
    }
}