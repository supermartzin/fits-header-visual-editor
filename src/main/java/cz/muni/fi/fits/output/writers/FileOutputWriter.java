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

    private static final File OUTPUT_FILE = new File("output.info");

    @Override
    public boolean writeInfo(String infoMessage) {
        try {
            OUTPUT_FILE.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "] INFO: " + infoMessage);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeException(Throwable exception) {
        return writeException(null, exception);
    }

    @Override
    public boolean writeException(String errorMessage, Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        try {
            OUTPUT_FILE.createNewFile();

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE, true)))) {
                writer.println("[" + LocalDateTime.now().toString() + "] ERROR > [" + exceptionType + "]: " + exception.getMessage());
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
}
