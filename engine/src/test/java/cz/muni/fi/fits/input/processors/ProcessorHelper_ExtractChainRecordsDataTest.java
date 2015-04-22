package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.inputData.ChainRecordsInputData;
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
 * Tests for extraction of input data for operation <b>Chain multiple records</b>
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ProcessorHelper_ExtractChainRecordsDataTest {

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
        exception.expectMessage("does not contain any keyword or constant");
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_DoesNotContainKeyword() throws Exception {
        String[] args = new String[] { "chain", "-s", FILE_PATH.toString(), "-c=CONSTANT 1", "COMMENT" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("Keyword is not specified");
        CmdArgumentsProcessorHelper.extractChainRecordsData(args);
    }

    @Test
    public void testExtractChainRecordsData_CorrectParameters1() throws Exception {
        String[] args = new String[] { "chain", "-s", FILE_PATH.toString(), "KEYWORD", "-c=CONSTANT 1", "-k=KEYWORD 1", "-k=KEYWORD 2", "-c=CONSTANT 2", "COMMENT" };

        ChainRecordsInputData crid = CmdArgumentsProcessorHelper.extractChainRecordsData(args);

        assertTrue(crid.skipIfChainKwNotExists());
        assertFalse(crid.updateIfExists());
        assertEquals("KEYWORD", crid.getKeyword());
        assertEquals(4, crid.getChainValues().size());
        assertEquals(ChainRecordsInputData.ChainValueType.CONSTANT, crid.getChainValues().get(0).getFirst());
        assertEquals("CONSTANT 1", crid.getChainValues().get(0).getSecond());
        assertEquals(ChainRecordsInputData.ChainValueType.KEYWORD, crid.getChainValues().get(1).getFirst());
        assertEquals("KEYWORD 1", crid.getChainValues().get(1).getSecond());
        assertEquals(ChainRecordsInputData.ChainValueType.KEYWORD, crid.getChainValues().get(2).getFirst());
        assertEquals("KEYWORD 2", crid.getChainValues().get(2).getSecond());
        assertEquals(ChainRecordsInputData.ChainValueType.CONSTANT, crid.getChainValues().get(3).getFirst());
        assertEquals("CONSTANT 2", crid.getChainValues().get(3).getSecond());
        assertEquals("COMMENT", crid.getComment());
    }

    @Test
    public void testExtractChainRecordsData_CorrectParameters2() throws Exception {
        String[] args = new String[] { "chain", "-u", FILE_PATH.toString(), "KEYWORD", "-c=CONSTANT 1", "-k=KEYWORD 1" };

        ChainRecordsInputData crid = CmdArgumentsProcessorHelper.extractChainRecordsData(args);

        assertFalse(crid.skipIfChainKwNotExists());
        assertTrue(crid.updateIfExists());
        assertEquals("KEYWORD", crid.getKeyword());
        assertEquals(2, crid.getChainValues().size());
        assertEquals(ChainRecordsInputData.ChainValueType.CONSTANT, crid.getChainValues().get(0).getFirst());
        assertEquals("CONSTANT 1", crid.getChainValues().get(0).getSecond());
        assertEquals(ChainRecordsInputData.ChainValueType.KEYWORD, crid.getChainValues().get(1).getFirst());
        assertEquals("KEYWORD 1", crid.getChainValues().get(1).getSecond());
        assertNull(crid.getComment());
    }
}
