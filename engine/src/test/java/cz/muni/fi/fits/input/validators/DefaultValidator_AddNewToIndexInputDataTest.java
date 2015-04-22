package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.AddNewToIndexInputData;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tests for validation of {@link cz.muni.fi.fits.models.inputData.AddNewToIndexInputData} input data
 * in {@link DefaultInputDataValidator} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultValidator_AddNewToIndexInputDataTest {

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
    public void testValidate_AddNewToIndexInputData_Null() throws Exception {
        AddNewToIndexInputData antiid = null;

        exception.expect(IllegalArgumentException.class);
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_NullFitsFiles() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENT", false, null);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_NoFitsFiles() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_InvalidIndex() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(0, "KEYWORD", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number bigger than 0");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_NullKeyword() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, null, "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_EmptyKeyword() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_KeywordWithInvalidChars() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD*", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongKeyword() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_NullValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", null, "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_EmptyStringValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongStringValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_StringValueWithInvalidChars() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUEčšľť", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_CommentWithInvalidChars() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENTčšľť", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongComment() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongCommentAndStringValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE VALUE VALUE VALUE VALUE VALUE VALUE", "COMMENT TO LONG - COMMENT TO LONG", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("Comment is too long");
        _validator.validate(antiid);
    }
}
