package cz.muni.fi.fits.output.writers;

import javax.inject.Singleton;
import java.io.*;
import java.time.LocalDateTime;

/**
 *
 * TODO description
 */
@Singleton
public class FileOutputWriter implements OutputWriter {

    private final File _outputFile;

    public FileOutputWriter(String filePath) {
        _outputFile = new File(filePath);
    }

    public FileOutputWriter(File outputFile) {
        _outputFile = outputFile;
    }

    @Override
    public boolean writeInfo(String infoMessage) {
        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "] INFO >> " + infoMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeInfo(File file, String infoMessage) {
        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " INFO >> [" + file.getName() + "]:" + infoMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeException(Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " ERROR >> [" + exceptionType + "]: " + exception.getMessage());
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeException(String errorMessage, Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " ERROR >> [" + exceptionType + "]: " + errorMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeException(File file, Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " ERROR >>" +
                        " [" + file.getName() + "] -" +
                        " [" + exceptionType + "]: " +
                        exception.getMessage());
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeError(String errorMessage) {
        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " ERROR >>" + errorMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeError(File file, String errorMessage) {
        try {
            _outputFile.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(_outputFile, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "]" +
                        " ERROR >>" +
                        " [" + file.getName() + "]: " +
                        errorMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
}
