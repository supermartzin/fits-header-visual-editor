package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.AddNewRecordInputData;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tests for validation of {@link cz.muni.fi.fits.models.inputData.AddNewRecordInputData} input data
 * in {@link DefaultInputDataValidator} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultValidator_AddNewRecordInputDataTest {

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
    public void testValidate_AddNewRecordInputData_Null() throws Exception {
        AddNewRecordInputData anrid = null;

        exception.expect(IllegalArgumentException.class);
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_NullFitsFiles() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMMENT", false, null);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_NoFitsFiles() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_NullKeyword() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData(null, "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_EmptyKeyword() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_KeywordWithInvalidChars() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEY WORD*", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongKeyword() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_NullValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", null, "", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_EmptyStringValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "", "", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongStringValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                "", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_StringValueWithInvalidChars() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUEýššá", null, false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_CommentWithInvalidChars() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMENTčšťščžťýž", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongComment() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongCommentAndStringValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE VALUE VALUE VALUE VALUE VALUE VALUE", "COMMENT TO LONG - COMMENT TO LONG", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("Comment is too long");
        _validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_ValidInputData() throws Exception {
        AddNewRecordInputData anrid1 = new AddNewRecordInputData("KEYWORD", 125.45, null, true, _fitsFiles);
        AddNewRecordInputData anrid2 = new AddNewRecordInputData("KEYWORD", "random value of testing record", "random comment", false, _fitsFiles);
        AddNewRecordInputData anrid3 = new AddNewRecordInputData("KEYWORD", true, "random comment", false, _fitsFiles);

        _validator.validate(anrid1);
        _validator.validate(anrid2);
        _validator.validate(anrid3);
    }
}
