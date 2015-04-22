package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.ChangeValueByKeywordInputData;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tests for validation of {@link cz.muni.fi.fits.models.inputData.ChangeValueByKeywordInputData} input data
 * in {@link DefaultInputDataValidator} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultValidator_ChangeValueByKeywordInputDataTest {

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
    public void testValidate_ChangeValueByKeywordInputData_Null() throws Exception {
        ChangeValueByKeywordInputData cvbkid = null;

        exception.expect(IllegalArgumentException.class);
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_NullFitsFiles() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENT", false, null);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_NoFitsFiles() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_NullKeyword() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData(null, "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_EmptyKeyword() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_KeywordWithInvalidChars() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD*", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongKeyword() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_NullValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", null, "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_EmptyStringValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "", "COMMENT", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongStringValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                "", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_StringValueWithInvalidChars() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUEščľščščšľ", null, false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_CommentWithInvalidChars() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENTščľščščšľ", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid non-ASCII characters");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongComment() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongCommentAndStringValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE VALUE VALUE VALUE VALUE VALUE", "COMMENT TO LONG - COMMENT TO LONG", false, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("Comment is too long");
        _validator.validate(cvbkid);
    }
}
