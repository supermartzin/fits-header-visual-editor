package cz.muni.fi.fits.output.writers;

import com.google.inject.Singleton;

import java.io.File;
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
    public boolean writeInfo(File file, String infoMessage) {
        System.out.println("[" + LocalDateTime.now().toString() + "]" +
                " >> [" + file.getName() + "]: " + infoMessage);

        return true;
    }

    @Override
    public boolean writeException(Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        System.err.println("[" + LocalDateTime.now().toString() + "]" +
                " >> [" + exceptionType + "]: " + exception.getMessage());

        return true;
    }

    @Override
    public boolean writeException(String errorMessage, Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        System.err.println("[" + LocalDateTime.now().toString() + "]" +
                " >> [" + exceptionType + "]: " + errorMessage);

        return true;
    }

    @Override
    public boolean writeException(File file, Throwable exception) {
        String exceptionType = exception.getClass().getTypeName();
        int lastIndex = exceptionType.lastIndexOf('.');
        if (lastIndex > -1)
            exceptionType = exceptionType.substring(lastIndex + 1);

        System.err.println("[" + LocalDateTime.now().toString() + "]" +
                " >>" +
                " [" + file.getName() + "] -" +
                " [" + exceptionType + "]: " +
                exception.getMessage());

        return true;
    }
}
