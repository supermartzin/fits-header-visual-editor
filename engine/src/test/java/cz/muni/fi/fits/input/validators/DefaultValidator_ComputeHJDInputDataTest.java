package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.DegreesObject;
import cz.muni.fi.fits.models.TimeObject;
import cz.muni.fi.fits.models.inputData.ComputeHJDInputData;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Tests for validation of {@link cz.muni.fi.fits.models.inputData.ComputeHJDInputData} input data
 * in {@link DefaultInputDataValidator} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultValidator_ComputeHJDInputDataTest {

    private static InputDataValidator _validator;
    private static Collection<File> _fitsFiles;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        _validator = new DefaultInputDataValidator();
        _fitsFiles = Sets.newHashSet(new File("sample1.fits"), new File("sample2.fits"));
    }


    @Test
    public void testValidate_ComputeHJDInputData_Null() throws Exception {
        ComputeHJDInputData chjdid = null;

        exception.expect(IllegalArgumentException.class);
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_NullFitsFiles() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment", null);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_NoFitsFiles() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment");

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DatetimeParameterNull() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData((String)null, 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DatetimeKeywordEmpty() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DatetimeKeywordWithInvalidChars() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATE TIME", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DatetimeKeywordTooLong() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("TOO_LONG_KEYWORD", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ExposureParameterNull() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", null,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ExposureValueNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", Double.NaN,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be a valid number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ExposureKeywordEmpty() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "",
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ExposureKeywordWithInvalidChars() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOS**E",
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ExposureKeywordTooLong() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE_TOO_LONG",
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_RightAscensionNull() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                null, new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_RightAscensionHoursNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(Double.NaN, 24.0, 5), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_RightAscensionMinutesNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, Double.NaN, 5), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_RightAscensionSecondsNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, 14, Double.NaN), new DegreesObject(-87, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DeclinationNull() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, 25.3, 6), null, "comment", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DeclinationDegreesNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, 24.0, 5), new DegreesObject(Double.NaN, 25, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DeclinationMinutesNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, 23, 5), new DegreesObject(-87, Double.NaN, 14.256), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_DeclinationSecondsNaN() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(12, 14, 23), new DegreesObject(-87, 25, Double.NaN), null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_CommentWithInvalidChars() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "commentčšľľšč", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_CommentTooLong() throws Exception {
        ComputeHJDInputData chjdid = new ComputeHJDInputData("DATETIME", 40.0,
                new TimeObject(12, 25.3, 6), new DegreesObject(-87, 25, 14.256), "comment too long - comment too long - comment too long", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(chjdid);
    }

    @Test
    public void testValidate_ComputeHJDInputData_ValidInputData() throws Exception {
        ComputeHJDInputData chjdid1 = new ComputeHJDInputData("DATETIME", 12.45,
                new TimeObject(12, 14.36, 23), new DegreesObject(0, 25, 89.12), "comment", _fitsFiles);
        ComputeHJDInputData chjdid2 = new ComputeHJDInputData(LocalDateTime.of(2014, 5, 5, 12, 34, 56), "EXPOSURE",
                new TimeObject(12, 14.36, 23), new DegreesObject(0, 0, 0), null, _fitsFiles);
        ComputeHJDInputData chjdid3 = new ComputeHJDInputData("DATETIME", "EXPOSURE",
                new TimeObject(0, 0.0, 0.0), new DegreesObject(-87.2, 25, 89.12), "comment", _fitsFiles);
        ComputeHJDInputData chjdid4 = new ComputeHJDInputData(LocalDateTime.of(2014, 5, 5, 12, 34, 56), 1000.56,
                new TimeObject(0, 0.0, 0.0), new DegreesObject(0, 0, 0.0), null, _fitsFiles);

        _validator.validate(chjdid1);
        _validator.validate(chjdid2);
        _validator.validate(chjdid3);
        _validator.validate(chjdid4);
    }
}