package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.DefaultTypeConverter;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.models.inputData.ComputeHJDInputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Tests for extraction of input data for operation <b>Compute Heliocentric Julian Date</b>
 * in {@link CmdArgumentsProcessorHelper} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ProcessorHelper_ExtractComputeHJDDataTest {

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
    public void testExtractComputeHJDData_WrongNumberOfParameters() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME" };

        exception.expect(WrongNumberOfParametersException.class);
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_LocalDateTimeDatetimeParameter() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "2015-05-08T12:34:56", "EXPOSURE", "14:22:30", "-15:-16:-17" };

        ComputeHJDInputData chjdid = CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
        assertTrue(chjdid.getDatetime() instanceof LocalDateTime);
        assertEquals(LocalDateTime.of(2015, 5, 8, 12, 34, 56), chjdid.getDatetime());
    }

    @Test
    public void testExtractComputeHJDData_StringDatetimeKeyword() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "14:22:30", "-15:-16:-17" };

        ComputeHJDInputData chjdid = CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
        assertTrue(chjdid.getDatetime() instanceof String);
        assertEquals("DATETIME", chjdid.getDatetime());
    }

    @Test
    public void testExtractComputeHJDData_DoubleExposureParameter() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "2015-05-08T12:34:56", "25.45", "14:22:30", "-15:-16:-17" };

        ComputeHJDInputData chjdid = CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
        assertTrue(chjdid.getExposure() instanceof Double);
        assertEquals(25.45, (double) chjdid.getExposure(), 0.0);
    }

    @Test
    public void testExtractComputeHJDData_StringExposureKeyword() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "14:22:30", "-15:-16:-17", "comment" };

        ComputeHJDInputData chjdid = CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
        assertTrue(chjdid.getExposure() instanceof String);
        assertEquals("EXPOSURE", chjdid.getExposure());
    }

    @Test
    public void testExtractComputeHJDData_InvalidRightAscensionParameter() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-rghtasc=14:22:30", "-dec=-15:-16:-17", "comment" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_InvalidRightAscensionValue1() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-ra=14:abc:30:12", "-dec=-15:-16:-17", "comment" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_InvalidRightAscensionValue2() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-ra=14:abc:30", "-dec=-15:-16:-17", "comment" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_InvalidDeclinationParameter() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-ra=14:22:30", "-decln=-15:-16:-17" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_InvalidDeclinationValue1() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-ra=14:22:30", "-dec=-15:-+16:-17:123" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_InvalidDeclinationValue2() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "EXPOSURE", "-ra=14:22:30", "-decln=-15:16,15:-17" };

        exception.expect(IllegalInputDataException.class);
        exception.expectMessage("is in invalid format");
        CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
    }

    @Test
    public void testExtractComputeHJDData_CorrectParameters() throws Exception {
        String[] args = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "12.48", "14:22:30", "-15:-16:-17", "comment" };

        ComputeHJDInputData chjdid = CmdArgumentsProcessorHelper.extractComputeHJDData(args, _converter);
        assertNotNull(chjdid);
        assertEquals("DATETIME", chjdid.getDatetime());
        assertEquals(12.48, (double)chjdid.getExposure(), 0.0);
        assertNotNull(chjdid.getRightAscension());
        assertEquals(14.0, chjdid.getRightAscension().getHours(), 0.0);
        assertEquals(22.0, chjdid.getRightAscension().getMinutes(), 0.0);
        assertEquals(30.0, chjdid.getRightAscension().getSeconds(), 0.0);
        assertNotNull(chjdid.getDeclination());
        assertEquals(-15.0, chjdid.getDeclination().getDegrees(), 0.0);
        assertEquals(-16.0, chjdid.getDeclination().getMinutes(), 0.0);
        assertEquals(-17.0, chjdid.getDeclination().getSeconds(), 0.0);
        assertEquals("comment", chjdid.getComment());
    }
}
