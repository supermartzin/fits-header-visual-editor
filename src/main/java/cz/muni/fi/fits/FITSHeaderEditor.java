package cz.muni.fi.fits;

import cz.muni.fi.fits.engine.HeaderEditingEngine;
import cz.muni.fi.fits.exceptions.EditingEngineException;
import cz.muni.fi.fits.exceptions.FitsFileException;
import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.ValidationException;
import cz.muni.fi.fits.input.processors.InputProcessor;
import cz.muni.fi.fits.input.validators.InputDataValidator;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.output.writers.OutputWriter;

import javax.inject.Inject;
import java.io.File;

/**
 *
 * TODO description
 */
public class FITSHeaderEditor {

    private final HeaderEditingEngine _headerEditingEngine;
    private final InputProcessor _inputProcessor;
    private final InputDataValidator _inputDataValidator;
    private final OutputWriter _outputWriter;

    @Inject
    public FITSHeaderEditor(HeaderEditingEngine headerEditingEngine, InputProcessor inputProcessor, InputDataValidator inputDataValidator, OutputWriter outputWriter) {
        this._headerEditingEngine = headerEditingEngine;
        this._inputProcessor = inputProcessor;
        this._inputDataValidator = inputDataValidator;
        this._outputWriter = outputWriter;

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> _outputWriter.writeException(e));
    }

    public void start() {
        try {
            // process input parameters
            InputData inputData = _inputProcessor.getProcessedInput();

            switch (inputData.getOperationType()) {
                case ADD_NEW_RECORD_TO_END:
                    AddNewRecordInputData anrid = (AddNewRecordInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(anrid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");

                    // insert into FITS files
                    for (File fitsFile : inputData.getFitsFiles()) {
                        try {
                            _headerEditingEngine.addNewRecord(
                                    anrid.getKeyword(),
                                    anrid.getValue(),
                                    anrid.getComment(),
                                    anrid.updateIfExists(),
                                    fitsFile);
                            _outputWriter.writeInfo(fitsFile.getName() + " - record successfully added to header");
                        } catch (FitsFileException ffEx) {
                            _outputWriter.writeException(ffEx.getFileName(), ffEx);
                        } catch (EditingEngineException eeEx) {
                            _outputWriter.writeException(eeEx);
                        }
                    }
                    break;

                case ADD_NEW_RECORD_TO_INDEX:
                    AddNewToIndexInputData antiid = (AddNewToIndexInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(antiid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;

                case REMOVE_RECORD_BY_KEYWORD:
                    RemoveByKeywordInputData rbkid = (RemoveByKeywordInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(rbkid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;

                case REMOVE_RECORD_BY_INDEX:
                    RemoveByIndexInputData rbiid = (RemoveByIndexInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(rbiid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;

                case CHANGE_KEYWORD:
                    ChangeKeywordInputData ckid = (ChangeKeywordInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(ckid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;

                case CHANGE_VALUE_BY_KEYWORD:
                    ChangeValueByKeywordInputData cvbkid = (ChangeValueByKeywordInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(cvbkid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;

                case CHAIN_RECORDS:
                    ChainRecordsInputData crid = (ChainRecordsInputData)inputData;
                    // validate input data
                    _inputDataValidator.validate(crid);
                    _outputWriter.writeInfo("Provided parameters are in correct format");
                    break;
            }
        } catch (IllegalInputDataException | ValidationException iidEx) {
            _outputWriter.writeException(iidEx);
        }
    }
}
