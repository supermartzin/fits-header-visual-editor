package cz.muni.fi.fits.gui.services;

import cz.muni.fi.fits.gui.listeners.OutputListener;
import cz.muni.fi.fits.gui.utils.Constants;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO insert description
 *
 * @author Martin Vrábel
 * @version 1.0
 */
public class ExecutionService extends Service {

    private final Task _task;

    private OutputListener _outputListener;

    public ExecutionService(final List<String> inputDataArguments,
                            final String engineFilepath,
                            final int numberOfFiles) {
        if (engineFilepath == null)
            throw new IllegalArgumentException("FITS Header Editor Engine JAR filepath is not set");
        if (inputDataArguments == null || inputDataArguments.isEmpty())
            throw new IllegalArgumentException("Input Data arguments are not set!");

        _task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String line;
                int counter = 0;
                int maxProcessLength = numberOfFiles + 2;

                List<String> arguments = new LinkedList<String>() {
                    {
                        addAll(Arrays.asList("java", "-jar", engineFilepath));
                        addAll(inputDataArguments);
                    }
                };

                // set working directory
                Path workingDirectory = Paths.get(engineFilepath).getParent();

                // set up and start engine process
                Process process = new ProcessBuilder(arguments)
                        .redirectErrorStream(true)
                        .directory(workingDirectory.toFile())
                        .start();

                // process standard output
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        new BufferedInputStream(process.getInputStream())))) {
                    while ((line = br.readLine()) != null) {
                        updateProgress(++counter, maxProcessLength);

                        if (line.contains(Constants.INFO_IDENTIFIER)) {
                            // raise onInfo event in registered listener
                            if (_outputListener != null)
                                _outputListener.onInfo(line);
                            continue;
                        }
                        if (line.contains(Constants.ERROR_IDENTIFIER)) {
                            // raise onError event in registered listener
                            if (_outputListener != null)
                                _outputListener.onError(line);
                            continue;
                        }
                        if (line.contains(Constants.EXCEPTION_IDENTIFIER)) {
                            // raise onInfo event in registered listener
                            if (_outputListener != null)
                                _outputListener.onException(line);
                            continue;
                        }

                        // raise onError event in registered listeners on unknown message
                        if (_outputListener != null)
                            _outputListener.onError(line);
                    }
                }

                if (counter < maxProcessLength)
                    updateProgress(1, 1);

                // wait for process to end
                process.waitFor();

                return null;
            }
        };
    }

    public void setOutputListener(OutputListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("Output listener is null");

        _outputListener = listener;
    }

    public Task getTask() {
        return _task;
    }

    @Override
    protected Task createTask() {
        return _task;
    }
}
