package cz.muni.fi.fits.input.validators;

import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.utils.Constants;

/**
 *
 * TODO description
 */
public class DefaultInputDataValidator implements InputDataValidator {

    @Override
    public void validate(AddNewRecordInputData addNewRecordInputData) throws ValidationException {
        if (addNewRecordInputData == null)
            throw new IllegalArgumentException("addNewRecordInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(addNewRecordInputData);

        // keyword cannot be empty
        if (addNewRecordInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // keyword cannot contain whitespaces
        if (addNewRecordInputData.getKeyword().contains(" "))
            throw new ValidationException("Keyword cannot contain whitespace characters");

        // check for allowed keyword length
        if (addNewRecordInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // value cannot be empty
        if (addNewRecordInputData.getValue().isEmpty())
            throw new ValidationException("Value cannot be empty");

        // check for allowed value length
        if (addNewRecordInputData.getValue().length() > Constants.MAX_VALUE_COMMENT_LENGTH)
            throw new ValidationException("Value has exceeded maximum allowed length of " + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");

        // if contains comment check for allowed value/comment length
        if (!addNewRecordInputData.getComment().isEmpty()) {
            if (addNewRecordInputData.getValue().length() + addNewRecordInputData.getComment().length()
                                > Constants.MAX_VALUE_COMMENT_LENGTH)
                throw new ValidationException("Comment along with value have exceeded maximum allowed length of " + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");
        }
    }

    @Override
    public void validate(AddNewToIndexInputData addNewToIndexInputData) throws ValidationException {
        if (addNewToIndexInputData == null)
            throw new IllegalArgumentException("addNewToIndexInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(addNewToIndexInputData);

        // index must be non-negative number
        if (addNewToIndexInputData.getIndex() < 0)
            throw new ValidationException("Index must be number bigger than 0");

        // keyword cannot be empty
        if (addNewToIndexInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // keyword cannot contain whitespaces
        if (addNewToIndexInputData.getKeyword().contains(" "))
            throw new ValidationException("Keyword cannot contain whitespace characters");

        // check for allowed keyword length
        if (addNewToIndexInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // value cannot be empty
        if (addNewToIndexInputData.getValue().isEmpty())
            throw new ValidationException("Value cannot be empty");

        // check for allowed value length
        if (addNewToIndexInputData.getValue().length() > Constants.MAX_VALUE_COMMENT_LENGTH)
            throw new ValidationException("Value has exceeded maximum allowed length of " + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");

        // if contains comment check for allowed value/comment length
        if (!addNewToIndexInputData.getComment().isEmpty()) {
            if (addNewToIndexInputData.getValue().length() + addNewToIndexInputData.getComment().length()
                                > Constants.MAX_VALUE_COMMENT_LENGTH)
                throw new ValidationException("Comment along with value have exceeded maximum allowed length of " + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");
        }
    }

    @Override
    public void validate(RemoveByKeywordInputData removeByKeywordInputData) throws ValidationException {
        if (removeByKeywordInputData == null)
            throw new IllegalArgumentException("removeByKeywordInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(removeByKeywordInputData);

        // keyword cannot be empty
        if (removeByKeywordInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // keyword cannot contain whitespaces
        if (removeByKeywordInputData.getKeyword().contains(" "))
            throw new ValidationException("Keyword cannot contain whitespace characters");

        // check for allowed keyword length
        if (removeByKeywordInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");
    }

    @Override
    public void validate(RemoveByIndexInputData removeByIndexInputData) throws ValidationException {
        if (removeByIndexInputData == null)
            throw new IllegalArgumentException("removeByIndexInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(removeByIndexInputData);

        // index must be non-negative number
        if (removeByIndexInputData.getIndex() < 0)
            throw new ValidationException("Index must be number bigger than 0");
    }

    @Override
    public void validate(ChangeKeywordInputData changeKeywordInputData) throws ValidationException {
        if (changeKeywordInputData == null)
            throw new IllegalArgumentException("changeKeywordInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(changeKeywordInputData);

        // old keyword cannot be empty
        if (changeKeywordInputData.getOldKeyword().isEmpty())
            throw new ValidationException("Old keyword cannot be empty");

        // new keyword cannot be empty
        if (changeKeywordInputData.getNewKeyword().isEmpty())
            throw new ValidationException("New keyword cannot be empty");

        // old keyword cannot contain whitespaces
        if (changeKeywordInputData.getOldKeyword().contains(" "))
            throw new ValidationException("Old keyword cannot contain whitespace characters");

        // new keyword cannot contain whitespaces
        if (changeKeywordInputData.getNewKeyword().contains(" "))
            throw new ValidationException("New keyword cannot contain whitespace characters");

        // check for allowed old keyword length
        if (changeKeywordInputData.getOldKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Old keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // check for allowed new keyword length
        if (changeKeywordInputData.getNewKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("New keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");
    }

    @Override
    public void validate(ChangeValueByKeywordInputData changeValueByKeywordInputData) {

    }

    @Override
    public void validate(ChainRecordsInputData chainRecordsInputData) {

    }

    private void validateCommonInputData(InputData inputData) throws ValidationException {
        if (inputData.getFitsFiles().isEmpty())
            throw new ValidationException("No FITS files provided for operation");
    }
}
