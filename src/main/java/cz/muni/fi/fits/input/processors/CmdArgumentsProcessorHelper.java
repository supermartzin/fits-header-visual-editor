package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.inputData.*;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
final class CmdArgumentsProcessorHelper {

    static Collection<File> extractFilesData(String pathToFile) throws IllegalInputDataException {
        if (pathToFile == null)
            throw new IllegalArgumentException("pathToFile is null");

        Collection<File> fitsFiles = new HashSet<>();

        // check existence of input file
        File inputFile = new File(pathToFile);
        if (!inputFile.exists())
            throw new IllegalInputDataException("Input file does not exist");
        if (!inputFile.isFile())
            throw new IllegalInputDataException("Provided path is not a file");

        // read paths to FITS files
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                File fitsFile = new File(line);
                fitsFiles.add(fitsFile);
            }
        } catch (FileNotFoundException fnfEx) {
            throw new IllegalInputDataException("Input file does not exist", fnfEx);
        } catch (IOException ioEx) {
            throw new IllegalInputDataException("Error reading input file", ioEx);
        }

        return fitsFiles;
    }

    static AddNewRecordInputData extractAddNewRecordData(String[] cmdArgs) throws IllegalInputDataException {
        if (cmdArgs.length != 4 && cmdArgs.length != 5 && cmdArgs.length != 6)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD'");

        // get switch (optional)
        boolean updateIfExists = false;
        String switchParam = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-u"))
                updateIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-u'");
        }

        // get keyword of new record (required)
        String keyword = !updateIfExists ? cmdArgs[2].trim() : cmdArgs[3].trim();

        // TODO to validator
        /*if (keyword.length() > Constants.MAX_KEYWORD_LENGTH)
            throw new IllegalInputDataException("Keyword parameter '"
                                                    + keyword + "' exceeds allowed length of "
                                                    + Constants.MAX_KEYWORD_LENGTH + " characters");*/

        // get value of new record (required)
        String value = !updateIfExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        // TODO to validator
        /*if (value.length() > Constants.MAX_VALUE_COMMENT_LENGTH)
            throw new IllegalInputDataException("Value parameter '"
                                                    + value + "' exceeds allowed length of "
                                                    + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");*/

        // get comment of new record (optional)
        String comment = "";
        if (!updateIfExists && cmdArgs.length == 5)
            comment = cmdArgs[4].trim();
        else if (updateIfExists && cmdArgs.length == 6)
            comment = cmdArgs[5].trim();

        // TODO to validator
            /*if (comment.length() + value.length() > Constants.MAX_VALUE_COMMENT_LENGTH)
                throw new IllegalInputDataException("Comment parameter '"
                                                        + comment + "' along with the value exceeds allowed length of "
                                                        + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");*/

        return new AddNewRecordInputData(keyword, value, comment, updateIfExists);
    }

    static AddNewToIndexInputData extractAddNewToIndexData(String[] cmdArgs) throws IllegalInputDataException {
        if (cmdArgs.length != 5 && cmdArgs.length != 6 && cmdArgs.length != 7)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD_IX'");

        // get switch (optional)
        boolean removeOldIfExists = false;
        String switchParam  = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-rm"))
                removeOldIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-rm'");
        }

        // get index where to add new record (required)
        int index;
        String indexString = !removeOldIfExists ? cmdArgs[2].trim() : cmdArgs[3].trim();
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException nfEx) {
            throw new IllegalInputDataException("Index is in invalid format: " + indexString, nfEx);
        }

        // get keyword of new record (required)
        String keyword = !removeOldIfExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        // get value of new record (required)
        String value = !removeOldIfExists ? cmdArgs[4].trim() : cmdArgs[5].trim();

        // get comment of new record (optional)
        String comment = "";
        if (!removeOldIfExists && cmdArgs.length == 6)
            comment = cmdArgs[5].trim();
        else if (removeOldIfExists & cmdArgs.length == 7)
            comment = cmdArgs[6].trim();

        return new AddNewToIndexInputData(index, keyword, value, comment, removeOldIfExists);
    }

    static RemoveByKeywordInputData extractRemoveByKeywordData(String[] cmdArgs) throws WrongNumberOfParametersException {
        if (cmdArgs.length != 3)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'REMOVE'");

        // get keyword of record to remove (required)
        String keyword = cmdArgs[2].trim();

        return new RemoveByKeywordInputData(keyword);
    }

    static RemoveByIndexInputData extractRemoveByIndexData(String[] cmdArgs) throws IllegalInputDataException {
        if (cmdArgs.length != 3)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'REMOVE_IX'");

        // get index where to add new record (required)
        int index;
        String indexString = cmdArgs[2].trim();
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException nfEx) {
            throw new IllegalInputDataException("Index is in invalid format: " + indexString, nfEx);
        }

        return new RemoveByIndexInputData(index);
    }

    static ChangeKeywordInputData extractChangeKeywordData(String[] cmdArgs) throws WrongNumberOfParametersException, InvalidSwitchParameterException {
        if (cmdArgs.length != 4 && cmdArgs.length != 5)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHANGE_KW'");

        // get switch (optional)
        boolean removeValueOfNewIfExists = false;
        String switchParam = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-rm"))
                removeValueOfNewIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-rm'");
        }

        // get old keyword (required)
        String oldKeyword = !removeValueOfNewIfExists ? cmdArgs[2].trim() : cmdArgs[3].trim();

        // get new keyword (required)
        String newKeyword = !removeValueOfNewIfExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        return new ChangeKeywordInputData(oldKeyword, newKeyword, removeValueOfNewIfExists);
    }

    static ChangeValueByKeywordInputData extractChangeValueByKeywordData(String[] cmdArgs) {
        return new ChangeValueByKeywordInputData(null);
    }

    static ChainRecordsInputData extractChainRecordsData(String[] cmdArgs) {
        return new ChainRecordsInputData(null);
    }
}
