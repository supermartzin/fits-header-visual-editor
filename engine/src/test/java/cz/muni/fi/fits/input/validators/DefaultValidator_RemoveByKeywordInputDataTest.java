package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.RemoveByKeywordInputData;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tests for validation of {@link cz.muni.fi.fits.models.inputData.RemoveByKeywordInputData} input data
 * in {@link DefaultInputDataValidator} class
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class DefaultValidator_RemoveByKeywordInputDataTest {

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
    public void testValidate_RemoveByKeywordInputData_Null() throws Exception {
        RemoveByKeywordInputData rbkid = null;

        exception.expect(IllegalArgumentException.class);
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_NullFitsFiles() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEYWORD", null);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_NoFitsFiles() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEYWORD", new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_NullKeyword() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData(null, _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be null");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_EmptyKeyword() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_KeywordWithInvalidChars() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEYWORD*", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("contains invalid characters");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_TooLongKeyword() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("TOO_LONG_KEYWORD", _fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        _validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_ValidInputData() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEYWORD", _fitsFiles);

        _validator.validate(rbkid);
    }
}
