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
     * @throws ValidationException
     */
    void validate(AddNewRecordInputData addNewRecordInputData) throws ValidationException;

    /**
     *
     * @param addNewToIndexInputData
     * @throws ValidationException
     */
    void validate(AddNewToIndexInputData addNewToIndexInputData) throws ValidationException;

    /**
     *
     * @param removeByKeywordInputData
     * @throws ValidationException
     */
    void validate(RemoveByKeywordInputData removeByKeywordInputData) throws ValidationException;

    /**
     *
     * @param removeFromIndexInputData
     * @throws ValidationException
     */
    void validate(RemoveFromIndexInputData removeFromIndexInputData) throws ValidationException;

    /**
     *
     * @param changeKeywordInputData
     * @throws ValidationException
     */
    void validate(ChangeKeywordInputData changeKeywordInputData) throws ValidationException;

    /**
     *
     * @param changeValueByKeywordInputData
     * @throws ValidationException
     */
    void validate(ChangeValueByKeywordInputData changeValueByKeywordInputData) throws ValidationException;

    /**
     *
     * @param chainRecordsInputData
     * @throws ValidationException
     */
    void validate(ChainRecordsInputData chainRecordsInputData) throws ValidationException;
}
