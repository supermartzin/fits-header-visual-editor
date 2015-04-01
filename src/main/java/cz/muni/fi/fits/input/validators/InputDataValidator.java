package cz.muni.fi.fits.input.validators;

import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.models.inputData.*;

/**
 *
 * TODO dscription
 */
public interface InputDataValidator {

    /**
     *
     * @param addNewRecordInputData
     */
    void validate(AddNewRecordInputData addNewRecordInputData) throws ValidationException;

    /**
     *
     * @param addNewToIndexInputData
     */
    void validate(AddNewToIndexInputData addNewToIndexInputData) throws ValidationException;

    /**
     *
     * @param removeByKeywordInputData
     */
    void validate(RemoveByKeywordInputData removeByKeywordInputData) throws ValidationException;

    /**
     *
     * @param removeByIndexInputData
     */
    void validate(RemoveByIndexInputData removeByIndexInputData) throws ValidationException;

    /**
     *
     * @param changeKeywordInputData
     */
    void validate(ChangeKeywordInputData changeKeywordInputData) throws ValidationException;

    /**
     *
     * @param changeValueByKeywordInputData
     */
    void validate(ChangeValueByKeywordInputData changeValueByKeywordInputData);

    /**
     *
     * @param chainRecordsInputData
     */
    void validate(ChainRecordsInputData chainRecordsInputData);
}
