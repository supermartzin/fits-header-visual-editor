package cz.muni.fi.fits.input.validators;

import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.utils.Constants;
import cz.muni.fi.fits.utils.Tuple;

/**
 *
 * TODO description
 */
public class DefaultInputDataValidator implements InputDataValidator {

    @Override
    public void validate(AddNewRecordInputData addNewRecordInputData) throws ValidationException {
        if (addNewRecordInputData == null)
            throw new IllegalArgumentException("addNewRecordInputData is null");

        boolean isValueString = false;

        // fits files collection cannot be empty
        validateCommonInputData(addNewRecordInputData);

        // keyword cannot be null
        if (addNewRecordInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (addNewRecordInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!addNewRecordInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

        // check for allowed keyword length
        if (addNewRecordInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // value cannot be null
        if (addNewRecordInputData.getValue() == null)
            throw new ValidationException("Value cannot be null");

        // if value is String, check for allowed value length
        Object value = addNewRecordInputData.getValue();
        if (value instanceof String) {
            isValueString = true;
            String strValue = (String)value;

            // String value cannot be empty
            if (strValue.isEmpty())
                throw new ValidationException("String value cannot be empty");
            // check for String value allowed length
            if (strValue.length() > Constants.MAX_STRING_VALUE_LENGTH)
                throw new ValidationException("String value has exceeded maximum allowed length of " + Constants.MAX_STRING_VALUE_LENGTH + " characters");
        }

        // comment cannot be null
        if (addNewRecordInputData.getComment() == null)
            throw new ValidationException("Comment cannot be null");

        // if contains comment check for allowed comment length
        if (!addNewRecordInputData.getComment().isEmpty()) {
            if (addNewRecordInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
            if (isValueString) {
                String strValue = (String)value;
                // check for allowed String value & comment length
                if (strValue.length() + addNewRecordInputData.getComment().length()
                        > Constants.MAX_STRING_VALUE_COMMENT_LENGTH) {
                    int maxLength = Constants.MAX_STRING_VALUE_COMMENT_LENGTH - strValue.length();
                    throw new ValidationException("Comment is too long. Maximum length of comment for this String value is " + maxLength + " characters");
                }
            }
        }
    }

    @Override
    public void validate(AddNewToIndexInputData addNewToIndexInputData) throws ValidationException {
        if (addNewToIndexInputData == null)
            throw new IllegalArgumentException("addNewToIndexInputData is null");

        boolean isValueString = false;

        // fits files collection cannot be empty
        validateCommonInputData(addNewToIndexInputData);

        // index must be non-negative number
        if (addNewToIndexInputData.getIndex() < 0)
            throw new ValidationException("Index must be number bigger than 0");

        // keyword cannot be null
        if (addNewToIndexInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (addNewToIndexInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!addNewToIndexInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

        // check for allowed keyword length
        if (addNewToIndexInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // value cannot be null
        if (addNewToIndexInputData.getValue() == null)
            throw new ValidationException("Value cannot be null");

        // if value is String, check for allowed value length
        Object value = addNewToIndexInputData.getValue();
        if (value instanceof String) {
            isValueString = true;
            String strValue = (String)value;

            // String value cannot be empty
            if (strValue.isEmpty())
                throw new ValidationException("String value cannot be empty");
            // check for String value allowed length
            if (strValue.length() > Constants.MAX_STRING_VALUE_LENGTH)
                throw new ValidationException("String value has exceeded maximum allowed length of " + Constants.MAX_STRING_VALUE_LENGTH + " characters");
        }

        // comment cannot be null
        if (addNewToIndexInputData.getComment() == null)
            throw new ValidationException("Comment cannot be null");

        // if contains comment check for allowed comment length
        if (!addNewToIndexInputData.getComment().isEmpty()) {
            if (addNewToIndexInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
            if (isValueString) {
                String strValue = (String)value;
                // check for allowed String value & comment length
                if (strValue.length() + addNewToIndexInputData.getComment().length()
                        > Constants.MAX_STRING_VALUE_COMMENT_LENGTH) {
                    int maxLength = Constants.MAX_STRING_VALUE_COMMENT_LENGTH - strValue.length();
                    throw new ValidationException("Comment is too long. Maximum length of comment for this String value is " + maxLength + " characters");
                }
            }
        }
    }

    @Override
    public void validate(RemoveByKeywordInputData removeByKeywordInputData) throws ValidationException {
        if (removeByKeywordInputData == null)
            throw new IllegalArgumentException("removeByKeywordInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(removeByKeywordInputData);

        // keyword cannot be null
        if (removeByKeywordInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (removeByKeywordInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!removeByKeywordInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

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

        // old keyword cannot be null
        if (changeKeywordInputData.getOldKeyword() == null)
            throw new ValidationException("Old keyword cannot be null");

        // old keyword cannot be empty
        if (changeKeywordInputData.getOldKeyword().isEmpty())
            throw new ValidationException("Old keyword cannot be empty");

        // new keyword cannot be null
        if (changeKeywordInputData.getNewKeyword() == null)
            throw new ValidationException("New keyword cannot be null");

        // new keyword cannot be empty
        if (changeKeywordInputData.getNewKeyword().isEmpty())
            throw new ValidationException("New keyword cannot be empty");

        // check for old keyword's allowed characters
        if (!changeKeywordInputData.getOldKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Old keyword contains invalid characters");

        // check for new keyword's allowed characters
        if (!changeKeywordInputData.getNewKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("New keyword contains invalid characters");

        // check for allowed old keyword length
        if (changeKeywordInputData.getOldKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Old keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // check for allowed new keyword length
        if (changeKeywordInputData.getNewKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("New keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");
    }

    @Override
    public void validate(ChangeValueByKeywordInputData changeValueByKeywordInputData) throws ValidationException {
        if (changeValueByKeywordInputData == null)
            throw new IllegalArgumentException("changeValueByKeywordInputData is null");

        boolean isValueString = false;

        // fits files collection cannot be empty
        validateCommonInputData(changeValueByKeywordInputData);

        // keyword cannot be null
        if (changeValueByKeywordInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (changeValueByKeywordInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!changeValueByKeywordInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

        // check for allowed keyword length
        if (changeValueByKeywordInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // value cannot be null
        if (changeValueByKeywordInputData.getValue() == null)
            throw new ValidationException("Value cannot be null");

        // if value is String, check for allowed value length
        Object value = changeValueByKeywordInputData.getValue();
        if (value instanceof String) {
            isValueString = true;
            String strValue = (String)value;

            // String value cannot be empty
            if (strValue.isEmpty())
                throw new ValidationException("String value cannot be empty");
            // check for String value allowed length
            if (strValue.length() > Constants.MAX_STRING_VALUE_LENGTH)
                throw new ValidationException("String value has exceeded maximum allowed length of " + Constants.MAX_STRING_VALUE_LENGTH + " characters");
        }

        // comment cannot be null
        if (changeValueByKeywordInputData.getComment() == null)
            throw new ValidationException("Comment cannot be null");

        // if contains comment check for allowed comment length
        if (!changeValueByKeywordInputData.getComment().isEmpty()) {
            if (changeValueByKeywordInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
            if (isValueString) {
                String strValue = (String)value;
                // check for allowed String value & comment length
                if (strValue.length() + changeValueByKeywordInputData.getComment().length()
                        > Constants.MAX_STRING_VALUE_COMMENT_LENGTH) {
                    int maxLength = Constants.MAX_STRING_VALUE_COMMENT_LENGTH - strValue.length();
                    throw new ValidationException("Comment is too long. Maximum length of comment for this String value is " + maxLength + " characters");
                }
            }
        }
    }

    @Override
    public void validate(ChainRecordsInputData chainRecordsInputData) throws ValidationException {
        if (chainRecordsInputData == null)
            throw new IllegalArgumentException("chainRecordsInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(chainRecordsInputData);

        // keyword cannot be null
        if (chainRecordsInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (chainRecordsInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!chainRecordsInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

        // check for allowed keyword length
        if (chainRecordsInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // chainValues cannot be null
        if (chainRecordsInputData.getChainValues() == null)
            throw new ValidationException("Chain values cannot be null");

        int constantsLength = 0;
        int keywordsLength = 0;
        for (Tuple tuple : chainRecordsInputData.getChainValues()) {
            if (tuple.getFirst().equals("constant")) {
                String constant = (String) tuple.getSecond();
                constantsLength += constant.length();
            }
            if (tuple.getFirst().equals("keyword")) {
                String keyword = (String) tuple.getSecond();
                keywordsLength += keyword.length();
            }
        }

        // chainValues cannot be empty
        if (chainRecordsInputData.getChainValues().isEmpty()
                || constantsLength + keywordsLength == 0)
            throw new ValidationException("Chain values cannot be empty");


        // check if constants in chainValues do not exceeds allowed length
        if (constantsLength > Constants.MAX_STRING_VALUE_LENGTH)
            throw new ValidationException("Constants in value of chained record have exceeded maximum allowed length of " + Constants.MAX_STRING_VALUE_LENGTH + " characters");

        // comment cannot be null
        if (chainRecordsInputData.getComment() == null)
            throw new ValidationException("Comment cannot be null");

        // if contains comment check for allowed value/comment length
        if (!chainRecordsInputData.getComment().isEmpty()) {
            if (chainRecordsInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
            if (chainRecordsInputData.getComment().length() + constantsLength
                                > Constants.MAX_STRING_VALUE_COMMENT_LENGTH)
                throw new ValidationException("Comment is too long for constants in value");
        }

    }

    private void validateCommonInputData(InputData inputData) throws ValidationException {
        if (inputData.getFitsFiles().isEmpty())
            throw new ValidationException("No FITS files provided for operation");
    }
}
