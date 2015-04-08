package cz.muni.fi.fits.output.writers;

import com.google.inject.Singleton;

import java.time.LocalDateTime;

/**
 *
 * TODO description
 */
@Singleton
public class ConsoleOutputWriter implements OutputWriter {

    @Override
    public boolean writeInfo(String infoMessage) {
        System.out.println("[" + LocalDateTime.now().toString() + "] >> " + infoMessage);

        return true;
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

        System.err.println("[" + LocalDateTime.now().toString() + "]" +
                " [" + exceptionType + "]" +
                " >> " + (errorMessage != null ? errorMessage + " | " : "")
                + exception.getMessage());

        return true;
    }
}
