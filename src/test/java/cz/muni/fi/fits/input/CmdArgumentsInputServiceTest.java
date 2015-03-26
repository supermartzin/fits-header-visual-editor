package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.UnknownOperationException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * TODO description
 */
public class CmdArgumentsInputServiceTest {

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
        InputService inputService = new CmdArgumentsInputService(null);

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("Arguments are null");
        inputService.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_NoArguments() throws Exception {
        InputService inputService = new CmdArgumentsInputService(new String[]{});

        exception.expect(WrongNumberOfParametersException.class);
        inputService.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_WrongNumberOfArguments() throws Exception {
        String[] args = new String[] { "add_bad" };
        InputService inputService = new CmdArgumentsInputService(args);

        exception.expect(WrongNumberOfParametersException.class);
        inputService.getProcessedInput();
    }

    @Test
    public void testGetProcessedInput_BadOperation() throws Exception {
        String[] args = new String[] { "add_bad", "files.in" };
        InputService inputService = new CmdArgumentsInputService(args);

        exception.expect(UnknownOperationException.class);
        inputService.getProcessedInput();
    }
}