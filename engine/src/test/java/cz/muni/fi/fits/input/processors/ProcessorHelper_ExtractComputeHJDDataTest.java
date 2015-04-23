package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.input.converters.DefaultTypeConverter;
import cz.muni.fi.fits.input.converters.TypeConverter;
import cz.muni.fi.fits.models.inputData.ComputeHJDInputData;
import cz.muni.fi.fits.utils.Constants;
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
 * @version 1.0.1
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
        String[] args1 = new String[] { "hjd", FILE_PATH.toString(), "DATETIME", "12.48", "14:22:30", "-15:-16:-17", "comment" };

        ComputeHJDInputData chjdid1 = CmdArgumentsProcessorHelper.extractComputeHJDData(args1, _converter);
        assertNotNull(chjdid1);
        assertEquals("DATETIME", chjdid1.getDatetime());
        assertEquals(12.48, (double)chjdid1.getExposure(), 0.0);
        assertNotNull(chjdid1.getRightAscension());
        assertEquals(14.0, chjdid1.getRightAscension().getHours(), 0.0);
        assertEquals(22.0, chjdid1.getRightAscension().getMinutes(), 0.0);
        assertEquals(30.0, chjdid1.getRightAscension().getSeconds(), 0.0);
        assertNotNull(chjdid1.getDeclination());
        assertEquals(-15.0, chjdid1.getDeclination().getDegrees(), 0.0);
        assertEquals(-16.0, chjdid1.getDeclination().getMinutes(), 0.0);
        assertEquals(-17.0, chjdid1.getDeclination().getSeconds(), 0.0);
        assertEquals("comment", chjdid1.getComment());


        String[] args2 = new String[] { "hjd", FILE_PATH.toString(), "1970-01-01T00:00:00.0", "EXPTIME", "14:-12:-30", "15:16:37" };

        ComputeHJDInputData chjdid2 = CmdArgumentsProcessorHelper.extractComputeHJDData(args2, _converter);
        assertNotNull(chjdid2);
        assertEquals(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0), chjdid2.getDatetime());
        assertEquals("EXPTIME", chjdid2.getExposure());
        assertNotNull(chjdid2.getRightAscension());
        assertEquals(14.0, chjdid2.getRightAscension().getHours(), 0.0);
        assertEquals(-12.0, chjdid2.getRightAscension().getMinutes(), 0.0);
        assertEquals(-30.0, chjdid2.getRightAscension().getSeconds(), 0.0);
        assertNotNull(chjdid2.getDeclination());
        assertEquals(15.0, chjdid2.getDeclination().getDegrees(), 0.0);
        assertEquals(16.0, chjdid2.getDeclination().getMinutes(), 0.0);
        assertEquals(37.0, chjdid2.getDeclination().getSeconds(), 0.0);
        assertEquals(Constants.DEFAULT_JD_COMMENT, chjdid2.getComment());
    }
}
