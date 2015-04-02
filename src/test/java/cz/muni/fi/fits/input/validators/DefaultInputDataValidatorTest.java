package cz.muni.fi.fits.input.validators;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.utils.Tuple;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * TODO description
 */
public class DefaultInputDataValidatorTest {

    private static InputDataValidator validator;
    private static Collection<File> fitsFiles;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        validator = new DefaultInputDataValidator();
        fitsFiles = Sets.newHashSet(new File("sample1.fits"), new File("sample2.fits"));
    }


    // validate 'AddNewRecordInputData'
    @Test
    public void testValidate_AddNewRecordInputData_NoFitsFiles() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_EmptyKeyword() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_KeywordWithWhitespaces() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEY WORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongKeyword() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_EmptyValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "", "", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongValue() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                "", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(anrid);
    }

    @Test
    public void testValidate_AddNewRecordInputData_TooLongValueComment() throws Exception {
        AddNewRecordInputData anrid = new AddNewRecordInputData("KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("have exceeded maximum allowed length");
        validator.validate(anrid);
    }


    // validate 'AddNewToIndexInputData'
    @Test
    public void testValidate_AddNewToIndexInputData_NoFitsFiles() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_InvalidIndex() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(-2, "KEYWORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number bigger than 0");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_EmptyKeyword() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_KeywordWithWhitespaces() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEY WORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongKeyword() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_EmptyValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongValue() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                        "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(antiid);
    }

    @Test
    public void testValidate_AddNewToIndexInputData_TooLongValueComment() throws Exception {
        AddNewToIndexInputData antiid = new AddNewToIndexInputData(2, "KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("have exceeded maximum allowed length");
        validator.validate(antiid);
    }


    // validate 'RemoveByKeywordInputData'
    @Test
    public void testValidate_RemoveByKeywordInputData_NoFitsFiles() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEYWORD", new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_EmptyKeyword() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("", fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_KeywordWithWhitespaces() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("KEY WORD", fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(rbkid);
    }

    @Test
    public void testValidate_RemoveByKeywordInputData_TooLongKeyword() throws Exception {
        RemoveByKeywordInputData rbkid = new RemoveByKeywordInputData("TOO_LONG_KEYWORD", fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(rbkid);
    }


    // validate 'RemoveByIndexInputData'
    @Test
    public void testValidate_RemoveByIndexInputData_NoFitsFiles() throws Exception {
        RemoveByIndexInputData rbiid = new RemoveByIndexInputData(2, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(rbiid);
    }

    @Test
    public void testValidate_RemoveByIndexInputData_InvalidIndex() throws Exception {
        RemoveByIndexInputData rbiid = new RemoveByIndexInputData(-2, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("must be number bigger than 0");
        validator.validate(rbiid);
    }


    // validate 'ChangeKeywordInputData'
    @Test
    public void testValidate_ChangeKeywordInputData_NoFitsFiles() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("OLD_KW", "NEW_KW", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_EmptyOldKeyword() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("", "NEW_KW", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_OldKeywordWithWhitespaces() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("OLD KW", "NEW_KW", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_TooLongOldKeyword() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("TOO_LONG_OLD_KEYWORD", "NEW_KW", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_EmptyNewKeyword() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("OLD_KW", "", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_NewKeywordWithWhitespaces() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("OLD_KW", "NEW KW", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(ckid);
    }

    @Test
    public void testValidate_ChangeKeywordInputData_TooLongNewKeyword() throws Exception {
        ChangeKeywordInputData ckid = new ChangeKeywordInputData("OLD_KW", "TOO_LONG_NEW_KEYWORD", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(ckid);
    }


    // validate 'ChangeValueByKeywordInputData'
    @Test
    public void testValidate_ChangeValueByKeywordInputData_NoFitsFiles() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENT", false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_EmptyKeyword() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_KeywordWithWhitespaces() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEY WORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongKeyword() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("TOO_LONG_KEYWORD", "VALUE", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_EmptyValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "", "COMMENT", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongValue() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE TOO LONG - VALUE",
                                "", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(cvbkid);
    }

    @Test
    public void testValidate_ChangeValueByKeywordInputData_TooLongValueComment() throws Exception {
        ChangeValueByKeywordInputData cvbkid = new ChangeValueByKeywordInputData("KEYWORD", "VALUE", "COMMENT TO LONG - COMMENT TO LONG - COMMENT TO LONG - COMMENT TOO", false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("have exceeded maximum allowed length");
        validator.validate(cvbkid);
    }


    // validate 'ChainRecordsInputData'
    @Test
    public void testValidate_ChainRecordsInputData_NoFitsFiles() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(new Tuple<>("constant", "constant 1"), new Tuple<>("keyword", "KEYWORD")));
        ChainRecordsInputData crid = new ChainRecordsInputData("KEYWORD", chainValues, "COMMENT", false, false, new HashSet<>());

        exception.expect(ValidationException.class);
        exception.expectMessage("No FITS files provided");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_EmptyKeyword() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(new Tuple<>("constant", "constant 1"), new Tuple<>("keyword", "KEYWORD")));
        ChainRecordsInputData crid = new ChainRecordsInputData("", chainValues, "COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_KeywordWithWhitespaces() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(new Tuple<>("constant", "constant 1"), new Tuple<>("keyword", "KEYWORD")));
        ChainRecordsInputData crid = new ChainRecordsInputData("KEY WORD", chainValues, "COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot contain whitespace");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_TooLongKeyword() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(new Tuple<>("constant", "constant 1"), new Tuple<>("keyword", "KEYWORD")));
        ChainRecordsInputData crid = new ChainRecordsInputData("TOO_LONG_KEYWORD", chainValues, "COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("has exceeded maximum allowed length");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_EmptyChainValues() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(new Tuple<>("constant", ""), new Tuple<>("keyword", "")));
        ChainRecordsInputData crid = new ChainRecordsInputData("KEYWORD", chainValues, "COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("cannot be empty");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_TooLongConstants() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(
                new Tuple<>("constant", "too long constant 1 - too long constant 1"),
                new Tuple<>("constant", "too long constant 2 - too long constant 2")));
        ChainRecordsInputData crid = new ChainRecordsInputData("KEYWORD", chainValues, "COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("have exceeded maximum allowed length");
        validator.validate(crid);
    }

    @Test
    public void testValidate_ChainRecordsInputData_TooLongConstantsComment() throws Exception {
        LinkedList<Tuple> chainValues = new LinkedList<>(Lists.<Tuple>newArrayList(
                new Tuple<>("constant", "constant 1"),
                new Tuple<>("keyword", "KEYWORD"),
                new Tuple<>("constant", "constant 2")));
        ChainRecordsInputData crid = new ChainRecordsInputData("KEYWORD", chainValues, "TOO LONG COMMENT - TOO LONG COMENT - TOO LONG COMMENT", false, false, fitsFiles);

        exception.expect(ValidationException.class);
        exception.expectMessage("have exceeded maximum allowed length");
        validator.validate(crid);
    }
}