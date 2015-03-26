package cz.muni.fi.fits.input;

import cz.muni.fi.fits.exceptions.WrongNumberOfParametersException;
import cz.muni.fi.fits.exceptions.IllegalInputDataException;
import cz.muni.fi.fits.models.OperationType;
import cz.muni.fi.fits.models.inputDataModels.AddNewRecordInputData;
import cz.muni.fi.fits.models.inputDataModels.AddNewToIndexInputData;
import cz.muni.fi.fits.utils.Constants;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * TODO description
 */
final class CmdArgsProcessingHelper {

    static Collection<File> extractFilesData(String pathToFile) throws IllegalInputDataException {
        if (pathToFile == null)
            throw new IllegalArgumentException("pathToFile is null");

        Collection<File> fitsFiles = new HashSet<>();

        // check existence of input file
        File inputFile = new File(pathToFile);
        if (!inputFile.exists())
            throw new IllegalInputDataException("Input file does not exist");
        if (!inputFile.isFile())
            throw new IllegalInputDataException("Provided path to input file is invalid");

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
        if (cmdArgs.length != 4 && cmdArgs.length != 5)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD_BY_KW'");

        // get keyword to add (required)
        String keyword = cmdArgs[2].trim();
        if (keyword.length() > Constants.MAX_KEYWORD_LENGTH)
            throw new IllegalInputDataException("Keyword parameter '"
                                                    + keyword + "' exceeds allowed length of "
                                                    + Constants.MAX_KEYWORD_LENGTH + " characters");

        // get value to add (required)
        String value = cmdArgs[3].trim();
        if (value.length() > Constants.MAX_VALUE_COMMENT_LENGTH)
            throw new IllegalInputDataException("Value parameter '"
                                                    + value + "' exceeds allowed length of "
                                                    + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");

        // get comment to add (optional)
        String comment = null;
        if (cmdArgs.length == 5) {
            comment = cmdArgs[4].trim();
            if (comment.length() + value.length() > Constants.MAX_VALUE_COMMENT_LENGTH)
                throw new IllegalInputDataException("Comment parameter '"
                                                        + comment + "' along with the value exceeds allowed length of "
                                                        + Constants.MAX_VALUE_COMMENT_LENGTH + " characters");
        }

        return new AddNewRecordInputData(keyword, value, comment);
    }

    static AddNewToIndexInputData extractAddNewToIndexData(String[] cmdArgs) throws IllegalInputDataException {
        if (cmdArgs.length != 5 && cmdArgs.length != 6)
            throw new WrongNumberOfParametersException(cmdArgs.length, "Wrong number of parameters for operation 'ADD_BY_KW'");

        return null;
    }
}
