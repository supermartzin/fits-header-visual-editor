package cz.muni.fi.fits.input.processors;

import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.exceptions.InvalidSwitchParameterException;
import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.models.inputData.*;
import cz.muni.fi.fits.utils.Tuple;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
        // get switch (optional)
        boolean updateIfExists = false;
        String switchParam = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-u"))
                updateIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-u'");
        }

        if (!updateIfExists) {
            if (cmdArgs.length < 4 || cmdArgs.length > 5)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD'");
        } else {
            if (cmdArgs.length < 5 || cmdArgs.length > 6)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD'");
        }

        // get keyword of new record (required)
        String keyword = !updateIfExists ? cmdArgs[2].trim() : cmdArgs[3].trim();

        // get value of new record (required)
        String value = !updateIfExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        // get comment of new record (optional)
        String comment = "";
        if (!updateIfExists && cmdArgs.length == 5)
            comment = cmdArgs[4].trim();
        else if (updateIfExists && cmdArgs.length == 6)
            comment = cmdArgs[5].trim();

        return new AddNewRecordInputData(keyword, value, comment, updateIfExists);
    }

    static AddNewToIndexInputData extractAddNewToIndexData(String[] cmdArgs) throws IllegalInputDataException {
        // get switch (optional)
        boolean removeOldIfExists = false;
        String switchParam  = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-rm"))
                removeOldIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-rm'");
        }

        if (!removeOldIfExists) {
            if (cmdArgs.length < 5 || cmdArgs.length > 6)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD_IX'");
        } else {
            if (cmdArgs.length < 6 || cmdArgs.length > 7)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD_IX'");
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
        // get switch (optional)
        boolean removeValueOfNewIfExists = false;
        String switchParam = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-rm"))
                removeValueOfNewIfExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-rm'");
        }

        if (!removeValueOfNewIfExists) {
            if (cmdArgs.length != 4)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHANGE_KW'");
        } else {
            if (cmdArgs.length != 5)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHANGE_KW'");
        }

        // get old keyword (required)
        String oldKeyword = !removeValueOfNewIfExists ? cmdArgs[2].trim() : cmdArgs[3].trim();

        // get new keyword (required)
        String newKeyword = !removeValueOfNewIfExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        return new ChangeKeywordInputData(oldKeyword, newKeyword, removeValueOfNewIfExists);
    }

    static ChangeValueByKeywordInputData extractChangeValueByKeywordData(String[] cmdArgs) throws WrongNumberOfParametersException, InvalidSwitchParameterException {
        // get switch (optional)
        boolean addNewIfNotExists = false;
        String switchParam = cmdArgs[1].trim();
        if (switchParam.startsWith("-")) {
            if (switchParam.equals("-a"))
                addNewIfNotExists = true;
            else
                throw new InvalidSwitchParameterException("Switch parameter is in invalid format: '" + switchParam + "'. Correct format is '-a'");
        }

        if (!addNewIfNotExists) {
            if (cmdArgs.length < 4 || cmdArgs.length > 5)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHANGE'");
        } else {
            if (cmdArgs.length < 5 || cmdArgs.length > 6)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHANGE'");
        }

        // get keyword of changing record (required)
        String keyword = !addNewIfNotExists ? cmdArgs[2].trim() : cmdArgs[3].trim();

        // get value of changing record (required)
        String value = !addNewIfNotExists ? cmdArgs[3].trim() : cmdArgs[4].trim();

        // get comment of changing record (optional)
        String comment = "";
        if (!addNewIfNotExists && cmdArgs.length == 5)
            comment = cmdArgs[4].trim();
        else if (addNewIfNotExists && cmdArgs.length == 6)
            comment = cmdArgs[5].trim();

        return new ChangeValueByKeywordInputData(keyword, value, comment,addNewIfNotExists);
    }

    static ChainRecordsInputData extractChainRecordsData(String[] cmdArgs) throws IllegalInputDataException {
        // get switches (optional)
        boolean updateIfExists = false;
        boolean skipIfChainKwNotExists = false;
        String firstSwitchParam = cmdArgs[1].trim();
        // some parameter on first place
        if (firstSwitchParam.startsWith("-")) {
            switch (firstSwitchParam) {
                case "-u":
                    updateIfExists = true;
                    break;
                case "-s":
                    skipIfChainKwNotExists = true;
                    break;
                default:
                    throw new InvalidSwitchParameterException("First switch parameter is in invalid format: '" + firstSwitchParam + "'. Correct format is '-u' or '-s");
            }

            if (cmdArgs.length < 3)
                throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHAIN'");

            String secondSwitchParam = cmdArgs[2].trim();
            // some parameter on second place
            if (secondSwitchParam.startsWith("-")) {
                // if first param is '-u' second must be '-s'
                if (updateIfExists) {
                    if (secondSwitchParam.equals("-s"))
                        skipIfChainKwNotExists = true;
                    else
                        throw new InvalidSwitchParameterException("Second switch parameter is in invalid format: '" + secondSwitchParam + "'. Correct format is '-s");
                    // if first param is '-s' second must be '-u'
                } else {
                    if (secondSwitchParam.equals("-u"))
                        updateIfExists = true;
                    else
                        throw new InvalidSwitchParameterException("Second switch parameter is in invalid format: '" + secondSwitchParam + "'. Correct format is '-u");
                }
            }
        }

        // set keyword of chained record (required)
        int keywordIndex = updateIfExists && skipIfChainKwNotExists
                ? 4
                : !updateIfExists && !skipIfChainKwNotExists ? 2 : 3;
        // check for correct number of parameters
        if (cmdArgs.length < keywordIndex + 1)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHAIN'");

        String keyword = cmdArgs[keywordIndex].trim();

        // set parameters to chain (required) and comment (optional)
        int startIndex = keywordIndex + 1;
        // check for correct number of parameters
        if (cmdArgs.length < startIndex + 1)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'CHAIN'");

        LinkedList<Tuple> chainValues = new LinkedList<>();
        String comment = "";
        for (int i = startIndex; i < cmdArgs.length; i++) {
            String argument = cmdArgs[i].trim();

            // constant
            if (argument.startsWith("-c=")) {
                argument = argument.substring(3);
                chainValues.add(new Tuple<>("constant", argument));
                continue;
            }

            // keyword
            if (argument.startsWith("-k=")) {
                argument = argument.substring(3);
                chainValues.add(new Tuple<>("keyword", argument.toUpperCase()));
                continue;
            }

            // comment (optional)
            if (i == cmdArgs.length - 1) {
                comment = argument;
                continue;
            }

            throw new IllegalInputDataException("Invalid parameter to chain: '" + argument + "'");
        }

        if (chainValues.isEmpty())
            throw new IllegalInputDataException("Parameters does not contain any keyword or constant to chain");

        return new ChainRecordsInputData(keyword, chainValues, comment, updateIfExists, skipIfChainKwNotExists);
    }
}
