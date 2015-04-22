package cz.muni.fi.fits.input.validators;

import com.google.common.base.CharMatcher;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.utils.Constants;
import cz.muni.fi.fits.utils.Tuple;

/**
 * Default implementation of {@link InputDataValidator} interface
 * for validation of input data
 *
 * @author Martin Vrábel
 * @version 1.1.1
 */
public class DefaultInputDataValidator implements InputDataValidator {

    /**
     * Validates input data for operation <b>Add new record</b>
     *
     * @param addNewRecordInputData input data to validate
     * @throws ValidationException  {@inheritDoc}
     */
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
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(strValue))
                throw new ValidationException("String value contains invalid non-ASCII characters");
        }

        // if contains comment check for allowed comment length
        if (addNewRecordInputData.getComment() != null && !addNewRecordInputData.getComment().isEmpty()) {
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(addNewRecordInputData.getComment()))
                throw new ValidationException("Comment contains invalid non-ASCII characters");
            // check for comment allowed length
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

    /**
     * Validates input data for operation <b>Add new record to specific index</b>
     *
     * @param addNewToIndexInputData    input data to validate
     * @throws ValidationException      {@inheritDoc}
     */
    @Override
    public void validate(AddNewToIndexInputData addNewToIndexInputData) throws ValidationException {
        if (addNewToIndexInputData == null)
            throw new IllegalArgumentException("addNewToIndexInputData is null");

        boolean isValueString = false;

        // fits files collection cannot be empty
        validateCommonInputData(addNewToIndexInputData);

        // index must be positive number
        if (addNewToIndexInputData.getIndex() <= 0)
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
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(strValue))
                throw new ValidationException("Comment contains invalid non-ASCII characters");
        }

        // if contains comment check for allowed comment length
        if (addNewToIndexInputData.getComment() != null && !addNewToIndexInputData.getComment().isEmpty()) {
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(addNewToIndexInputData.getComment()))
                throw new ValidationException("Comment contains invalid non-ASCII characters");
            // check for comment allowed length
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

    /**
     * Validates input data for operation <b>Remove record by keyword</b>
     *
     * @param removeByKeywordInputData  input data to validate
     * @throws ValidationException      {@inheritDoc}
     */
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

    /**
     * Validates input data for operation <b>Remove record from specific index</b>
     *
     * @param removeFromIndexInputData  input data to validate
     * @throws ValidationException      {@inheritDoc}
     */
    @Override
    public void validate(RemoveFromIndexInputData removeFromIndexInputData) throws ValidationException {
        if (removeFromIndexInputData == null)
            throw new IllegalArgumentException("removeFromIndexInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(removeFromIndexInputData);

        // index must be positive number
        if (removeFromIndexInputData.getIndex() <= 0)
            throw new ValidationException("Index must be number bigger than 0");
    }

    /**
     * Validates input data for operation <b>Change keyword of record</b>
     *
     * @param changeKeywordInputData    input data to validate
     * @throws ValidationException      {@inheritDoc}
     */
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

    /**
     * Validates input data for operation <b>Change value of record</b>
     *
     * @param changeValueByKeywordInputData input data to validate
     * @throws ValidationException          {@inheritDoc}
     */
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
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(strValue))
                throw new ValidationException("String value contains invalid non-ASCII characters");
        }

        // if contains comment check for allowed comment length
        if (changeValueByKeywordInputData.getComment() != null && !changeValueByKeywordInputData.getComment().isEmpty()) {
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(changeValueByKeywordInputData.getComment()))
                throw new ValidationException("Comment contains invalid non-ASCII characters");
            // check for comment allowed length
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

    /**
     * Validates input data for operation <b>Chain multiple records</b>
     *
     * @param chainRecordsInputData input data to validate
     * @throws ValidationException  {@inheritDoc}
     */
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
            if (tuple.getFirst() == ChainRecordsInputData.ChainValueType.CONSTANT) {
                String constant = (String) tuple.getSecond();

                // constant cannot be null
                if (constant == null)
                    throw new ValidationException("Constant in chain values cannot be null");
                // check for invalid characters
                if (!CharMatcher.ASCII.matchesAllOf(constant))
                    throw new ValidationException("Constant '" + constant + "' in chain values  contains invalid non-ASCII characters");

                constantsLength += constant.length();
            } else if (tuple.getFirst() == ChainRecordsInputData.ChainValueType.KEYWORD) {
                String keyword = (String) tuple.getSecond();

                // keyword cannot be null
                if (keyword == null)
                    throw new ValidationException("Keyword in chain values cannot be null");
                // keyword cannot be empty
                if (keyword.isEmpty())
                    throw new ValidationException("Keyword in chain values cannot be empty");
                // check for keyword's allowed characters
                if (!keyword.matches(Constants.KEYWORD_REGEX))
                    throw new ValidationException("Keyword '" + keyword + "' in chain values contains invalid characters");
                // check for allowed keyword length
                if (keyword.length() > Constants.MAX_KEYWORD_LENGTH)
                    throw new ValidationException("Keyword '" + keyword + "' in chain values has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

                keywordsLength += keyword.length();
            } else {
                throw new ValidationException("Chain values contains unknown value");
            }
        }

        // chainValues cannot be empty
        if (chainRecordsInputData.getChainValues().isEmpty()
                || constantsLength + keywordsLength == 0)
            throw new ValidationException("Chain values cannot be empty");


        // check if constants in chainValues do not exceeds allowed length
        if (constantsLength > Constants.MAX_STRING_VALUE_LENGTH)
            throw new ValidationException("Constants in value of chained record have exceeded maximum allowed length of " + Constants.MAX_STRING_VALUE_LENGTH + " characters");

        // if contains comment check for allowed value/comment length
        if (chainRecordsInputData.getComment() != null && !chainRecordsInputData.getComment().isEmpty()) {
            // check for invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(chainRecordsInputData.getComment()))
                throw new ValidationException("Comment contains invalid non-ASCII characters");
            // check for comment allowed length
            if (chainRecordsInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
            // check for allowed constants & comment length
            if (chainRecordsInputData.getComment().length() + constantsLength
                                > Constants.MAX_STRING_VALUE_COMMENT_LENGTH)
                throw new ValidationException("Comment is too long for constants in value");
        }
    }

    /**
     * Validates input data for operation <b>Shift time of time record</b>
     *
     * @param shiftTimeInputData    input data to validate
     * @throws ValidationException  {@inheritDoc}
     */
    @Override
    public void validate(ShiftTimeInputData shiftTimeInputData) throws ValidationException {
        if (shiftTimeInputData == null)
            throw new IllegalArgumentException("shiftTimeInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(shiftTimeInputData);

        // keyword cannot be null
        if (shiftTimeInputData.getKeyword() == null)
            throw new ValidationException("Keyword cannot be null");

        // keyword cannot be empty
        if (shiftTimeInputData.getKeyword().isEmpty())
            throw new ValidationException("Keyword cannot be empty");

        // check for keyword's allowed characters
        if (!shiftTimeInputData.getKeyword().matches(Constants.KEYWORD_REGEX))
            throw new ValidationException("Keyword contains invalid characters");

        // check for allowed keyword length
        if (shiftTimeInputData.getKeyword().length() > Constants.MAX_KEYWORD_LENGTH)
            throw new ValidationException("Keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");

        // check if time shift parameters contains only 0's
        if (shiftTimeInputData.getYearShift() == 0
                && shiftTimeInputData.getMonthShift() == 0
                && shiftTimeInputData.getDayShift() == 0
                && shiftTimeInputData.getHourShift() == 0
                && shiftTimeInputData.getMinuteShift() == 0
                && shiftTimeInputData.getSecondShift() == 0
                && shiftTimeInputData.getMilisecondsShift() == 0
                & shiftTimeInputData.getNanosecondShift() == 0)
            throw new ValidationException("No time shift is specified");
    }

    /**
     * Validates input data for operation <b>Compute Heliocentric Julian Date</b>
     *
     * @param computeHJDInputData   input data to validate
     * @throws ValidationException  {@inheritDoc}
     */
    @Override
    public void validate(ComputeHJDInputData computeHJDInputData) throws ValidationException {
        if (computeHJDInputData == null)
            throw new IllegalArgumentException("computeHJDInputData is null");

        // fits files collection cannot be empty
        validateCommonInputData(computeHJDInputData);

        // Datetime parameter cannot be null
        if (computeHJDInputData.getDatetime() == null)
            throw new ValidationException("DateTime parameter cannot be null");

        if (computeHJDInputData.getDatetime() instanceof String) {
            String datetime = (String)computeHJDInputData.getDatetime();

            // String datetime keyword cannot be empty
            if (datetime.isEmpty())
                throw new ValidationException("DateTime keyword cannot be empty");

            // String datetime keyword cannot contain invalid characters
            if (!datetime.matches(Constants.KEYWORD_REGEX))
                throw new ValidationException("DateTime keyword contains invalid characters");

            // String datetime keyword cannot exceed allowed length
            if (datetime.length() > Constants.MAX_KEYWORD_LENGTH)
                throw new ValidationException("DateTime keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");
        }

        // Exposure parameter cannot be null
        if (computeHJDInputData.getExposure() == null)
            throw new ValidationException("Exposure parameter cannot be null");

        if (computeHJDInputData.getExposure() instanceof Double) {
            double exposure = (Double)computeHJDInputData.getExposure();

            // double exposure value must be number
            if (Double.isNaN(exposure))
                throw new ValidationException("Exposure value must be a valid number");
        }

        if (computeHJDInputData.getExposure() instanceof String) {
            String exposure = (String)computeHJDInputData.getExposure();

            // String exposure keyword cannot be empty
            if (exposure.isEmpty())
                throw new ValidationException("Exposure keyword cannot be empty");

            // String exposure keyword cannot contain invalid characters
            if (!exposure.matches(Constants.KEYWORD_REGEX))
                throw new ValidationException("Exposure keyword contains invalid characters");

            // String exposure keyword cannot exceed allowed length
            if (exposure.length() > Constants.MAX_KEYWORD_LENGTH)
                throw new ValidationException("Exposure keyword has exceeded maximum allowed length of " + Constants.MAX_KEYWORD_LENGTH + " characters");
        }

        // right ascension parameter cannot be null
        if (computeHJDInputData.getRightAscension() == null)
            throw new ValidationException("Right ascension parameter cannot be null");

        // right ascension parameter values must be numbers
        if (Double.isNaN(computeHJDInputData.getRightAscension().getHours()))
            throw new ValidationException("Right ascension's hours parameter must be number");
        if (Double.isNaN(computeHJDInputData.getRightAscension().getMinutes()))
            throw new ValidationException("Right ascension's minutes parameter must be number");
        if (Double.isNaN(computeHJDInputData.getRightAscension().getSeconds()))
            throw new ValidationException("Right ascension's seconds parameter must be number");

        // declination parameter cannot be null
        if (computeHJDInputData.getDeclination() == null)
            throw new ValidationException("Declination parameter cannot be null");

        // declination parameter values must be numbers
        if (Double.isNaN(computeHJDInputData.getDeclination().getDegrees()))
            throw new ValidationException("Declination's degrees parameter must be number");
        if (Double.isNaN(computeHJDInputData.getDeclination().getMinutes()))
            throw new ValidationException("Declination's minutes parameter must be number");
        if (Double.isNaN(computeHJDInputData.getDeclination().getSeconds()))
            throw new ValidationException("Declination's seconds parameter must be number");

        // check comment
        if (computeHJDInputData.getComment() != null
                && !computeHJDInputData.getComment().equals(Constants.DEFAULT_HJD_COMMENT)) {
            // check for comment's invalid characters
            if (!CharMatcher.ASCII.matchesAllOf(computeHJDInputData.getComment()))
                throw new ValidationException("Comment contains invalid non-ASCII characters");

            // check for comment's allowed length
            if (computeHJDInputData.getComment().length() > Constants.MAX_COMMENT_LENGTH)
                throw new ValidationException("Comment value has exceeded maximum allowed length of " + Constants.MAX_COMMENT_LENGTH + " characters");
        }
    }

    /**
     * Validates correctness of input FITS files in input data
     *
     * @param inputData             input data to validate
     * @throws ValidationException  when FITS files of input data are in invalid form
     */
    private void validateCommonInputData(InputData inputData) throws ValidationException {
        if (inputData.getFitsFiles() == null)
            throw new ValidationException("FITS files argument cannot be null");
        if (inputData.getFitsFiles().isEmpty())
            throw new ValidationException("No FITS files provided for operation");
    }
}
